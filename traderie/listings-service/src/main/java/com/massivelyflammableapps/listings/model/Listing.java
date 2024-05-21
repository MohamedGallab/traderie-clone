package com.massivelyflammableapps.listings.model;

import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import com.massivelyflammableapps.shared.dto.listings.ProductAmountDTO;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;
import com.massivelyflammableapps.shared.dto.offers.OfferedProductDTO;
import com.massivelyflammableapps.shared.resources.STATE;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table
public class Listing {
        @PrimaryKeyColumn
        private UUID listingId = UUID.randomUUID();
        @NonNull
        private UUID userId;
        @NonNull
        private UUID gameId;
        @NonNull
        private UUID productId;
        @NonNull
        private boolean buying;
        private String timestamp = new Date().toString();
        @NonNull
        private String productName;
        @NonNull
        private String productIcon;
        @NonNull
        private int quantity;
        @NonNull
        private List<List<ProductAmountDTO>> desiredOffer;
        @NonNull
        private STATE state;

        public Listing(ListingDTO listingDTO) {
                this.listingId = UUID.randomUUID();
                this.listingId = listingDTO.getListingId();
                this.userId = listingDTO.getUserId();
                this.gameId = listingDTO.getGameId();
                this.productId = listingDTO.getProductId();
                this.buying = listingDTO.isBuying();
                this.timestamp = new Date().toString();
                this.productName = listingDTO.getProductName();
                this.productIcon = listingDTO.getProductIcon();
                this.quantity = listingDTO.getQuantity();
                List<List<ProductAmountDTO>> desiredOffer = new ArrayList<>();
                for (List<ProductAmountDTO> productAmountDTOList : listingDTO.getDesiredOffer()) {
                        List<ProductAmountDTO> productAmountList = new ArrayList<>();
                        for (ProductAmountDTO productAmountDTO : productAmountDTOList) {
                                productAmountList.add(productAmountDTO);
                        }
                        desiredOffer.add(productAmountList);
                }

                this.desiredOffer = desiredOffer;
                this.state = listingDTO.getState();
        }

        public ListingDTO toDTO() {
                List<List<ProductAmountDTO>> desiredOffer = new ArrayList<>();
                for (List<ProductAmountDTO> productAmountList : this.desiredOffer) {
                        List<ProductAmountDTO> productAmountDTOList = new ArrayList<>();
                        for (ProductAmountDTO productAmount : productAmountList) {
                                productAmountDTOList.add(productAmount);
                        }
                        desiredOffer.add(productAmountDTOList);
                }
                return new ListingDTO(userId, gameId, productId, buying, timestamp, listingId, productName, productIcon,
                                quantity, desiredOffer, state);
        }
}

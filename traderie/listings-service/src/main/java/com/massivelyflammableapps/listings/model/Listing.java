package com.massivelyflammableapps.listings.model;

import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import com.massivelyflammableapps.shared.dto.listings.ProductAmountDTO;
import com.massivelyflammableapps.shared.resources.STATE;

import lombok.*;

import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
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
        @PrimaryKey
        private UUID listingId = UUID.randomUUID();
        @NonNull
        private UUID userId;
        @NonNull
        private UUID gameId;
        @NonNull
        private UUID productId;
        @NonNull
        private Boolean buying;
        private String timestamp = new Date().toString();
        @NonNull
        private String productName;
        @NonNull
        private String productIcon;
        @NonNull
        private Integer quantity;
        @NonNull
        @Frozen
        private List<List<ProductAmount>> desiredOffer;
        @NonNull
        private STATE state;

        public Listing(ListingDTO listingDTO) {
                this.listingId = UUID.randomUUID();
                this.userId = listingDTO.getUserId();
                this.gameId = listingDTO.getGameId();
                this.productId = listingDTO.getProductId();
                this.buying = listingDTO.isBuying();
                this.timestamp = new Date().toString();
                this.productName = listingDTO.getProductName();
                this.productIcon = listingDTO.getProductIcon();
                this.quantity = listingDTO.getQuantity();
                List<List<ProductAmount>> desiredOffer = new ArrayList<>();
                for (List<ProductAmountDTO> productAmountDTOList : listingDTO.getDesiredOffer()) {
                        List<ProductAmount> productAmountList = new ArrayList<>();
                        for (ProductAmountDTO productAmountDTO : productAmountDTOList) {
                                productAmountList.add(new ProductAmount(productAmountDTO));
                        }
                        desiredOffer.add(productAmountList);
                }

                this.desiredOffer = desiredOffer;
                this.state = listingDTO.getState();
        }

        public ListingDTO toDTO() {
                List<List<ProductAmountDTO>> desiredOfferDTO = new ArrayList<>();
                for (List<ProductAmount> productAmountList : desiredOffer) {
                        List<ProductAmountDTO> productAmountListDTO = new ArrayList<>();
                        for (ProductAmount productAmount : productAmountList) {
                                productAmountListDTO.add(productAmount.toDTO());
                        }
                        desiredOfferDTO.add(productAmountListDTO);

                }
                return new ListingDTO(userId, gameId, productId, buying, timestamp, listingId, productName, productIcon,
                                quantity, desiredOfferDTO, state);
        }
}

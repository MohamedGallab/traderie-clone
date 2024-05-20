package com.massivelyflammableapps.listings.model;

import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import com.massivelyflammableapps.shared.dto.listings.ProductAmountDTO;
import com.massivelyflammableapps.shared.resources.STATE;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table
public class ListingByGameByProduct {
        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private UUID productId;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private Boolean buying;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private UUID gameId;

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        @NonNull
        private UUID listingId = UUID.randomUUID();

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        @NonNull
        private String timestamp = new Date().toString();

        @NonNull
        private String productName;

        @NonNull
        private String productIcon;

        @NonNull
        private Integer quantity;

        @NonNull
        private UUID userId;

        @NonNull
        @Frozen
        private List<List<ProductAmount>> desiredOffer;
        @NonNull
        @Setter
        private STATE state = STATE.ACTIVE;

        public ListingByGameByProduct(ListingDTO listingDTO) {
                this.productId = listingDTO.getProductId();
                this.buying = listingDTO.isBuying();
                this.gameId = listingDTO.getGameId();
                this.productName = listingDTO.getProductName();
                this.productIcon = listingDTO.getProductIcon();
                this.quantity = listingDTO.getQuantity();
                this.userId = listingDTO.getUserId();
                this.desiredOffer = new ArrayList<>();
                List<List<ProductAmount>> desiredOffer = new ArrayList<>();
                for (List<ProductAmountDTO> productAmountListDTO : listingDTO.getDesiredOffer()) {
                        List<ProductAmount> productAmountList = new ArrayList<>();
                        for (ProductAmountDTO productAmountDTO : productAmountListDTO) {
                                productAmountList.add(new ProductAmount(productAmountDTO));
                        }
                        desiredOffer.add(productAmountList);
                }
                this.desiredOffer = desiredOffer;
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

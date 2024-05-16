package com.massivelyflammableapps.listings.model;

import com.massivelyflammableapps.resources.STATE;
import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import com.massivelyflammableapps.shared.dto.listings.ProductAmountDTO;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Frozen;
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
public class ListingByGameByUser {
        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private UUID userId;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private UUID gameId;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private boolean buying;

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        @NonNull
        private final String timestamp = new Date().toString();

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        @NonNull
        private UUID listingId;

        @NonNull
        private String productName ;

        @NonNull
        private String productIcon ;

        @NonNull
        private int quantity ;

        @NonNull
        @Frozen
        private List<@Frozen List<ProductAmount>> desiredOffer;

        @NonNull
        private STATE state = STATE.ACTIVE;

        public ListingByGameByUser (ListingDTO listingDTO){
                this.userId = listingDTO.getUserId();
                this.gameId = listingDTO.getGameId();
                this.buying = listingDTO.isBuying();
                this.listingId = listingDTO.getListingId();
                this.productName = listingDTO.getProductName();
                this.productIcon = listingDTO.getProductIcon();
                this.quantity = listingDTO.getQuantity();
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

        public ListingDTO toDTO(){
                List<List<ProductAmountDTO>> desiredOfferDTO = new ArrayList<>();
                for (List<ProductAmount>  productAmountList : desiredOffer) {
                        List <ProductAmountDTO> productAmountListDTO = new ArrayList<>();
                        for (ProductAmount productAmount: productAmountList) {
                                productAmountListDTO.add(productAmount.toDTO());
                        }
                        desiredOfferDTO.add(productAmountListDTO);

                }
                return new ListingDTO(userId,gameId,null,buying,timestamp,listingId,productName,productIcon,quantity,desiredOfferDTO,state);
        }
}

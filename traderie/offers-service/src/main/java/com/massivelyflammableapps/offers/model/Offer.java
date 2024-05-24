package com.massivelyflammableapps.offers.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.massivelyflammableapps.shared.dto.offers.OfferDTO;
import com.massivelyflammableapps.shared.dto.offers.OfferedProductDTO;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table
public class Offer {
    @PrimaryKey
    private UUID id = UUID.randomUUID();

    @NonNull
    private UUID listingId;

    @NonNull
    private UUID buyerId;

    @NonNull
    private UUID sellerId;

    private String timestamp = new Date().toString();

    @NonNull
    private String status;

    @NonNull
    @Frozen
    private List<List<OfferedProduct>> offeredProducts;

    public Offer(OfferDTO offerDTO) {
        this.listingId = offerDTO.getListingId();
        this.buyerId = offerDTO.getBuyerId();
        this.sellerId = offerDTO.getSellerId();
        this.status = offerDTO.getStatus();
        List<List<OfferedProduct>> offeredProducts = new ArrayList<>();
        for (List<OfferedProductDTO> offeredProductDTOList : offerDTO.getOfferedProducts()) {
            List<OfferedProduct> offeredProductList = new ArrayList<>();
            for (OfferedProductDTO offeredProductDTO : offeredProductDTOList) {
                offeredProductList.add(new OfferedProduct(offeredProductDTO));
            }
            offeredProducts.add(offeredProductList);
        }
        this.offeredProducts = offeredProducts;
    }

    public OfferDTO toDTO() {
        List<List<OfferedProductDTO>> offeredProductsDTO = new ArrayList<>();
        for (List<OfferedProduct> offeredProductList : offeredProducts) {
            List<OfferedProductDTO> offeredProductDTOList = new ArrayList<>();
            for (OfferedProduct offeredProduct : offeredProductList) {
                offeredProductDTOList.add(offeredProduct.toDTO());
            }
            offeredProductsDTO.add(offeredProductDTOList);
        }
        return new OfferDTO(id, listingId, buyerId, sellerId, timestamp, status, offeredProductsDTO);
    }
}

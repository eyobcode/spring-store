package com.codewitheyob.store.mappers;

import com.codewitheyob.store.dtos.ProductDto;
import com.codewitheyob.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);
}

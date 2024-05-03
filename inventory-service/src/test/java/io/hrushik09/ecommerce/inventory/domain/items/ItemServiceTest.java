package io.hrushik09.ecommerce.inventory.domain.items;

import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemCommand;
import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemResponse;
import io.hrushik09.ecommerce.inventory.web.items.exceptions.ItemAlreadyExists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.hrushik09.ecommerce.inventory.domain.items.ItemEntityBuilder.anItemEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    private ItemService itemService;
    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemService = new ItemService(itemRepository);
    }

    @Nested
    class CreateItem {
        @Test
        void shouldThrowWhenItemWithNameAndCategoryAlreadyExists() {
            String name = "existing_name";
            String category = "existing_category";
            when(itemRepository.existsByNameAndCategory(name, category)).thenReturn(true);

            assertThatThrownBy(() -> itemService.create(new CreateItemCommand(name, category, 5)))
                    .isInstanceOf(ItemAlreadyExists.class)
                    .hasMessage("Item existing_name already exists in category existing_category");
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingItem() {
            String name = "Samsung Galaxy 10";
            String category = "Mobile";
            int quantity = 6;
            when(itemRepository.existsByNameAndCategory(name, category)).thenReturn(false);
            when(itemRepository.save(any(ItemEntity.class)))
                    .thenReturn(anItemEntity().withName(name).withCategory(category).withQuantity(quantity).build());

            itemService.create(new CreateItemCommand(name, category, quantity));

            ArgumentCaptor<ItemEntity> itemEntityArgumentCaptor = ArgumentCaptor.forClass(ItemEntity.class);
            verify(itemRepository).save(itemEntityArgumentCaptor.capture());
            ItemEntity captorValue = itemEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).startsWith("item_");
            assertThat(captorValue.getName()).isEqualTo(name);
            assertThat(captorValue.getCategory()).isEqualTo(category);
            assertThat(captorValue.getQuantity()).isEqualTo(quantity);
        }

        @Test
        void shouldReturnCreatedItem() {
            String name = "Samsung Galaxy 10";
            String category = "Mobile";
            int quantity = 6;
            when(itemRepository.existsByNameAndCategory(name, category)).thenReturn(false);
            when(itemRepository.save(any(ItemEntity.class)))
                    .thenReturn(anItemEntity().withName(name).withCategory(category).withQuantity(quantity).build());

            CreateItemResponse created = itemService.create(new CreateItemCommand(name, category, quantity));

            assertThat(created).isNotNull();
            assertThat(created.code()).isNotNull();
            assertThat(created.name()).isEqualTo(name);
            assertThat(created.category()).isEqualTo(category);
            assertThat(created.quantity()).isEqualTo(quantity);
        }
    }
}
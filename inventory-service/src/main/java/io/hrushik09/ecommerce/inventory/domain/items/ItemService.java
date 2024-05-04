package io.hrushik09.ecommerce.inventory.domain.items;

import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemCommand;
import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemResponse;
import io.hrushik09.ecommerce.inventory.web.items.exceptions.ItemAlreadyExists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public CreateItemResponse create(CreateItemCommand cmd) {
        if (itemRepository.existsByNameAndCategory(cmd.name(), cmd.category())) {
            throw new ItemAlreadyExists(cmd.name(), cmd.category());
        }
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.assignCode();
        itemEntity.setName(cmd.name());
        itemEntity.setCategory(cmd.category());
        itemEntity.setQuantity(cmd.quantity());
        ItemEntity saved = itemRepository.save(itemEntity);
        return ItemMapper.convert(saved);
    }
}

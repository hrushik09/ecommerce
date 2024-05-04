package io.hrushik09.ecommerce.inventory.domain.items;

import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemCommand;
import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemResponse;
import io.hrushik09.ecommerce.inventory.web.items.exceptions.ItemAlreadyExists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ItemService {
    private static final Logger log = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public CreateItemResponse create(CreateItemCommand cmd) {
        if (itemRepository.existsByNameAndCategory(cmd.name(), cmd.category())) {
            log.error("Item with name {} and category {} already exists", cmd.name(), cmd.category());
            throw new ItemAlreadyExists(cmd.name(), cmd.category());
        }
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.assignCode();
        itemEntity.setName(cmd.name());
        itemEntity.setCategory(cmd.category());
        itemEntity.setQuantity(cmd.quantity());
        ItemEntity saved = itemRepository.save(itemEntity);
        log.info("Created new item with name {} and category {}", saved.getName(), saved.getCategory());
        return ItemMapper.convert(saved);
    }
}

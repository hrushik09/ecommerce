document.addEventListener("alpine:init", () => {
    Alpine.data("initData", (warehouseCode, needsRefrigeration) => ({
        warehouseCode: warehouseCode,
        needsRefrigeration: needsRefrigeration,
        inventoryItem: {
            productCode: null,
            quantityAvailable: null,
            minimumStockLevel: null,
            maximumStockLevel: null,
            reorderPoint: null
        },
        products: [],
        createInventoryItemErrorDetail: null,
        init() {
            this.getProducts(this.needsRefrigeration);
        },
        getProducts(needsRefrigeration) {
            $.getJSON("/api/inventory/products/all?needsRefrigeration=" + needsRefrigeration, (resp) => {
                this.products = resp;
            });
        },
        createInventoryItem() {
            $.ajax({
                url: "/api/inventory/warehouses/" + this.warehouseCode + "/items",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(this.inventoryItem),
                success: (response) => {
                    this.createInventoryItemErrorDetail = null;
                    window.location = "/inventory/warehouses/" + this.warehouseCode;
                },
                error: (err) => {
                    let responseText = JSON.parse(err.responseText);
                    this.createInventoryItemErrorDetail = responseText.detail;
                },
            });
        },
    }));
});
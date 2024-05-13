document.addEventListener("alpine:init", () => {
    Alpine.data("initData", (locationCode) => ({
        locationCode: locationCode,
        warehouse: {
            name: null,
            isRefrigerated: false,
        },
        createWarehouseErrorDetail: null,
        createWarehouse() {
            $.ajax({
                url: "/api/inventory/locations/" + this.locationCode + "/warehouses",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(this.warehouse),
                success: (response) => {
                    console.log("response ", response);
                    this.createWarehouseErrorDetail = null;
                },
                error: (err) => {
                    let responseText = JSON.parse(err.responseText);
                    this.createWarehouseErrorDetail = responseText.detail;
                },
            });
        },
    }));
});
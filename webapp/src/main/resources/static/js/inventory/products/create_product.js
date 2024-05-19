document.addEventListener("alpine:init", () => {
    Alpine.data("initData", () => ({
        product: {
            name: null,
            description: null,
            category: null,
            reorderQuantity: null,
            needsRefrigeration: false,
            measurement: {
                packedWeight: {
                    value: null,
                    unit: null,
                },
                packedLength: {
                    value: null,
                    unit: null,
                },
                packedWidth: {
                    value: null,
                    unit: null,
                },
                packedHeight: {
                    value: null,
                    unit: null,
                },
            }
        },
        createProductErrorDetail: null,
        createProduct() {
            $.ajax({
                url: "/api/inventory/products",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(this.product),
                success: (response) => {
                    this.createProductErrorDetail = null;
                    window.location = "/inventory/products/" + response.code;
                },
                error: (err) => {
                    let responseText = JSON.parse(err.responseText);
                    this.createProductErrorDetail = responseText.detail;
                },
            });
        },
    }));
});
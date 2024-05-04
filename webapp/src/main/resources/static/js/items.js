document.addEventListener('alpine:init', () => {
    Alpine.data('initData', () => ({
        item: {
            name: null,
            category: null,
            quantity: null
        },
        createItem() {
            $.ajax({
                url: "/api/items",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(this.item),
                success: (response) => {
                    console.log("Item created successfully!");
                    console.log(response);
                },
                error: (err) => {
                    let responseText = JSON.parse(err.responseText);
                    console.log(responseText.detail);
                },
            });
        },
    }));
});
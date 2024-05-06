document.addEventListener("alpine:init", () => {
    Alpine.data("initData", () => ({
        location: {
            name: null,
            address: null,
        },
        createLocation() {
            $.ajax({
                url: "/api/inventory/locations",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(this.location),
                success: (response) => {
                    console.log("Location created successfully!");
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
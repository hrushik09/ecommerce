document.addEventListener("alpine:init", () => {
    Alpine.data("initData", () => ({
        location: {
            name: null,
            address: null,
        },
        createLocationErrorDetail: null,
        createLocation() {
            $.ajax({
                url: "/api/inventory/locations",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(this.location),
                success: (response) => {
                    window.location = "/inventory/locations/" + response.code;
                },
                error: (err) => {
                    let responseText = JSON.parse(err.responseText);
                    this.createLocationErrorDetail = responseText.detail;
                },
            });
        },
    }));
});
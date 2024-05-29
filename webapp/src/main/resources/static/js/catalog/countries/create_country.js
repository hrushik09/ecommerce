document.addEventListener("alpine:init", () => {
    Alpine.data("initData", () => ({
        country: {
            name: null,
        },
        createCountryErrorDetail: null,
        createCountry() {
            $.ajax({
                url: "/api/catalog/countries",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(this.country),
                success: (response) => {
                    this.createCountryErrorDetail = null;
                    window.location = "/catalog/countries/" + response.code;
                },
                error: (err) => {
                    let responseText = JSON.parse(err.responseText);
                    this.createCountryErrorDetail = responseText.detail;
                },
            });
        },
    }));
});
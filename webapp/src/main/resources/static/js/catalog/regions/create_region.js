document.addEventListener("alpine:init", () => {
    Alpine.data("initData", (countryCode) => ({
        countryCode: countryCode,
        region: {
            name: null,
        },
        createRegionErrorDetail: null,
        createRegion() {
            $.ajax({
                url: "/api/catalog/countries/" + this.countryCode + "/regions",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(this.region),
                success: (response) => {
                    this.createRegionErrorDetail = null;
                    window.location = "/catalog/regions/" + response.code;
                },
                error: (err) => {
                    let responseText = JSON.parse(err.responseText);
                    this.createRegionErrorDetail = responseText.detail;
                },
            });
        },
    }));
});
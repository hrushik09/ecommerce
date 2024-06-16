document.addEventListener("alpine:init", () => {
    Alpine.data("initData", () => ({
        customer: {
            username: null,
            email: null,
            firstName: null,
            lastName: null,
            country: null,
            region: null,
        },
        createCustomerErrorDetail: null,
        createCustomer() {
            $.ajax({
                url: "/api/user/customers",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(this.customer),
                success: (response) => {
                    this.createCustomerErrorDetail = null;
                    window.location = "/user/customers/" + response.code;
                },
                error: (err) => {
                    let responseText = JSON.parse(err.responseText);
                    this.createCustomerErrorDetail = responseText.detail;
                },
            });
        },
    }));
});
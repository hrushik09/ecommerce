document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (pageNo) => ({
        pageNo: pageNo,
        init() {
            console.log("pageNo ", pageNo);
        },
    }));
});
export const parseApiError = async (response) => {
    const contentType = response.headers.get("content-type") || "";

    if (contentType.includes("application/json")) {
        const json = await response.json();

        return {
            type: "json",
            data: json,
        };
    } else {
        const text = await response.text();

        return {
            type: "text",
            message: text,
        };
    }
};
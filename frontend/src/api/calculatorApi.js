import {parseApiError} from "./helper";

const BASE = "/api/calculator";

export const createSession = async () => {
    const res = await fetch(`${BASE}/session`, { method: "POST" });
    if (!res.ok) throw new Error("Failed to create session");
    return res.json();
};

export const calculate = async (data) => {
    const res = await fetch(`${BASE}/calculate`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });

    if (!res.ok) {
        const error = await parseApiError(res);
        throw error;
    }

    return res.json();
};

export const restoreSession = async (sessionId) => {
    const res = await fetch(`${BASE}/restore/${sessionId}`);

    if (!res.ok) {
        const error = await parseApiError(res);
        throw error;

    }
    return res.json();
};
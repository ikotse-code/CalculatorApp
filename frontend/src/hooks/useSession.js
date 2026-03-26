import { useEffect, useState } from "react";
import { restoreSession } from "../api/calculatorApi";

const STORAGE_KEY = "sessionId";

export const useSession = () => {
    const [sessionId, setSessionIdState] = useState(null);
    const [data, setData] = useState(null);
    const [error, setError] = useState(null); // session errors

    const clearSession = () => {
        setSessionIdState(null);
        setData(null);
        setError(null);
        localStorage.removeItem(STORAGE_KEY);

        // clear sessionId from URL and reload
        window.location.href = window.location.origin;
    };

    const cleanUrl = () => {
        const url = new URL(window.location.href);
        url.searchParams.delete("sessionId");
        window.history.replaceState({}, "", url.toString());
    };

    useEffect(() => {

        const init = async () => {
            const params = new URLSearchParams(window.location.search);
            const urlSessionId = params.get("sessionId");
            const storedSessionId = localStorage.getItem(STORAGE_KEY);

            const tryRestore = async (id, isFromUrl = false) => {
                if (!id) return null;

                try {
                    const restored = await restoreSession(id);

                    setSessionIdState(restored.sessionId);
                    setData(restored);
                    localStorage.setItem(STORAGE_KEY, restored.sessionId);

                    if (isFromUrl) cleanUrl();

                    return restored;
                } catch (err) {
                    console.warn("Restore failed:", err.message);

                    setError(err.message);

                    return null;
                }
            };

            let result = null;

            if (urlSessionId) {
                result = await tryRestore(urlSessionId, true);
                if (!result) return;
            }

            // localStorage
            if (!result && storedSessionId) {
                result = await tryRestore(storedSessionId, false);
            }
        };

        void init();
    }, []);

    return {
        sessionId,
        setSessionIdState,
        data,
        setData,
        error,
        setError,
        clearSession,
    };
};
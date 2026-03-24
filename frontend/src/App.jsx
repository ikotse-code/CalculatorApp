import { useState, useEffect } from "react";
import { calculate, createSession } from "./api/calculatorApi";
import { useSession } from "./hooks/useSession";

import Form from "./components/Form";
import CardList from "./components/CardList";
import Summary from "./components/Summary";
import ShareBox from "./components/ShareBox";
import ErrorSection from "./components/ErrorSection";
import { makeShareLink } from "./utils/shareLink";

export default function App() {
    const [form, setForm] = useState({
        grossSalary: "",
        birthDate: "",
    });

    const [shareUrl, setShareUrl] = useState("");
    const [formErrors, setFormErrors] = useState({});

    const {
        sessionId,
        setSessionIdState,
        data,
        setData,
        error: sessionError,
        clearSession,
    } = useSession();

    // sync form with restored data
    useEffect(() => {
        if (data) {
            setForm({
                grossSalary: data.grossSalary ?? "",
                birthDate: data.birthDate ?? "",
            });
        }
    }, [data]);

    const handleCalculate = async () => {
        try {
            let currentSessionId = sessionId;

            if (!currentSessionId) {
                const newSession = await createSession();
                currentSessionId = newSession.sessionId;

                setSessionIdState(currentSessionId);
                localStorage.setItem("sessionId", currentSessionId);
            }

            const result = await calculate({
                sessionId: currentSessionId,
                grossSalary: Number(form.grossSalary),
                birthDate: form.birthDate,
            });

            setData(result);
            setFormErrors({});
        } catch (err) {
            console.log("API ERROR:", err);

            // JSON
            if (err.type === "json" && err.data) {
                const data = err.data;

                // { errors: { ... } }
                if (data.errors && typeof data.errors === "object") {
                    setFormErrors(data.errors);
                    return;
                }

                // { birthDate: "...", grossSalary: "..." }
                const isFieldErrorObject =
                    typeof data === "object" &&
                    !Array.isArray(data) &&
                    Object.values(data).every((v) => typeof v === "string");

                if (isFieldErrorObject) {
                    setFormErrors(data);
                    return;
                }

                // { message: "..." }
                if (data.message) {
                    setFormErrors({ general: data.message });
                    return;
                }

                // fallback
                setFormErrors({ general: "Unknown form error" });
                return;
            }

            // TEXT / NETWORK / 500
            if (err.type === "text") {
                setFormErrors({ general: err.message || "Server error" });
                return;
            }

            setFormErrors({ general: "Unexpected error" });
        }
    };

    const handleShare = () => {
        if (!sessionId || !data) return;
        const url = makeShareLink(sessionId);
        setShareUrl(url);
    };

    const copy = () => navigator.clipboard.writeText(shareUrl);

    return (
        <main className="container">
            {/* session / restore */}
            {sessionError ? (
                <ErrorSection message={sessionError} onReset={clearSession} />
            ) : (
                <>
                    {/* form errors */}
                    {Object.keys(formErrors).length > 0 && (
                        <ErrorSection message={formErrors.general || ""} />
                    )}

                    <Form
                        form={form}
                        setForm={setForm}
                        onSubmit={handleCalculate}
                        errors={formErrors} // highlighting
                        setFormErrors={setFormErrors}
                    />

                    <ShareBox
                        url={shareUrl}
                        onShare={handleShare}
                        onCopy={copy}
                        disabled={!sessionId || !data}
                        tooltip={!sessionId || !data ? "Calculate first to enable sharing" : ""}
                    />

                    {data && (
                        <section className="results" aria-labelledby="results-heading">
                            <h2 id="results-heading" className="sr-only">
                                Calculation Results
                            </h2>
                            <Summary total={data?.totalAmount || 0} />
                            <CardList
                                items={data?.monthlyBenefits || []}
                                totalAmount={data?.totalAmount || 0}
                                userSalary={data?.grossSalary || 0}
                            />
                        </section>
                    )}
                </>
            )}
        </main>
    );
}
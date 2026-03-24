import { useState, useEffect, useRef } from "react";

export default function ShareBox({ url, onShare, disabled, tooltip }) {
    const [copied, setCopied] = useState(false);
    const [showToast, setShowToast] = useState(false);
    const inputRef = useRef(null);

    useEffect(() => {
        let timer;
        if (copied) {
            timer = setTimeout(() => {
                setCopied(false);
                setShowToast(false);
            }, 2000);
        }
        return () => clearTimeout(timer);
    }, [copied]);

    const handleCopy = async () => {
        if (!url) return;

        try {
            await navigator.clipboard.writeText(url);
            setCopied(true);
            setShowToast(true);
        } catch (err) {
            console.error("Copy link failed", err);
        }
    };

    return (
        <section className="share" aria-labelledby="share-heading">
            <h2 id="share-heading" className="sr-only">Share your calculation</h2>

            <div className="share-controls">
                <button
                    id="share-button"
                    className="share-button"
                    type="button"
                    name="share"
                    aria-label="Generate shareable link"
                    onClick={onShare}
                    disabled={disabled}
                    title={tooltip}
                    style={{
                        cursor: disabled ? "not-allowed" : "pointer",
                        opacity: disabled ? 0.5 : 1,
                    }}
                    data-testid="share-button"
                >
                    Share
                </button>

                {url && (
                    <div className="share-box">
                        <label htmlFor="share-input" className="sr-only">Shareable link</label>
                        <input
                            id="share-input"
                            className="share-input"
                            type="text"
                            name="share-input"
                            value={url}
                            readOnly
                            ref={inputRef}
                            onClick={() => inputRef.current.select()}
                            data-testid="share-input"
                        />
                        <button
                            id="copy-button"
                            className={`copy-button ${copied ? "copied" : ""}`}
                            type="button"
                            name="copy"
                            aria-label="Copy link"
                            onClick={handleCopy}
                            data-testid="copy-button"
                        >
                            {copied ? "Copied!" : "Copy"}
                        </button>
                    </div>
                )}
            </div>

            {showToast && (
                <div className="toast" role="alert" aria-live="assertive">
                    Copied!
                </div>
            )}
        </section>
    );
}
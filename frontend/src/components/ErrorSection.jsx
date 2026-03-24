export default function ErrorSection({ message, onReset }) {
    if (!message) return null;

    return (
        <section className="errors" role="alert" aria-labelledby="error-heading">
            <h2 id="error-heading" className="sr-only">Error Message</h2>
            <p>{message}</p>

            {onReset && (
                <button
                    id="reset-button"
                    type="button"
                    name="reset-button"
                    aria-describedby="error-heading"
                    aria-label="Clear session and start over"
                    onClick={onReset}
                    data-testid="reset-session-button"
                >
                    Reset session
                </button>
            )}
        </section>
    );
}
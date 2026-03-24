export default function Summary({ total }) {
    return (
        <section className="summary" aria-labelledby="summary-heading">
            <h2 id="summary-heading">Total Amount</h2>
            <div
                className="summary-value"
                aria-live="polite" // announce updates for sr
                data-testid="total-amount"
            >
                {total.toFixed(2)} €
            </div>
        </section>
    );
}
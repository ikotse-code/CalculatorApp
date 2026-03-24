export default function Card({ item, index, totalAmount, userSalary, onSelect }) {

    const handleKeyPress = (e) => {
        if (e.key === "Enter" || e.key === " ") {
            e.preventDefault();
            if (onSelect) onSelect(item);
        }
    };

    return (
        <article
            className="card"
            tabIndex={0} // клавиатурный фокус
            role="listitem" // интерактивная карточка
            aria-labelledby={`card-title-${index}`}
            aria-describedby={`card-details-${index}`}
            onKeyDown={handleKeyPress} // Enter/Space поддержка
            onClick={() => onSelect && onSelect(item)}
            data-testid={`card-${item.month}`}
            style={{ animationDelay: `${index * 120}ms` }}
        >
            <div className="card-wrapper">
                <h3 id={`card-title-${index}`} className="sr-only">
                    Month: {item.month}
                </h3>
                <div id={`card-details-${index}`} className="card-content">
                    <p className="card-month">Month: {item.month}</p>
                    <p className="card-amount">Amount: {item.amount.toFixed(2)} €</p>
                    <p className="card-days">{item.daysPaid} days</p>
                    <p className="card-total">Total amount per year: {totalAmount.toFixed(2)} €</p>
                </div>
            </div>
        </article>
    );
}
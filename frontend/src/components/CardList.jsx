import Card from "./Card";

export default function CardList({ items, totalAmount, userSalary }) {
    return (
        <section
            className="card-list"
            aria-label="Monthly Benefits Cards"
        >
            <h2 className="sr-only">Monthly Benefits</h2>
            <div role="list" className="cards-wrapper" data-testid="card-list">
                {items.map((item, i) => (
                    <Card
                        key={item.month}
                        item={item}
                        index={i}
                        totalAmount={totalAmount}
                        userSalary={userSalary}
                        role="listitem"
                    />
                ))}
            </div>
        </section>
    );
}
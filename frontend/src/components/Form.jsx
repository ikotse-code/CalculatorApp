export default function Form({ form, setForm, onSubmit, errors = {}, setFormErrors }) {
    return (
        <section className="calculator" aria-labelledby="calculator-heading">
            <h2 id="calculator-heading" className="sr-only">
                Benefit Calculator
            </h2>

            <form
                className="form"
                onSubmit={(e) => {
                    e.preventDefault();
                    onSubmit();
                }}
                aria-label="Benefit Calculator"
            >
                <div className="form-group">
                        <label htmlFor="grossSalary">Salary (€)</label>
                        <input
                            id="grossSalary"
                            type="number"
                            name="grossSalary"
                            value={form.grossSalary}
                            placeholder="Salary"
                            aria-invalid={!!errors.grossSalary}
                            aria-describedby={errors.grossSalary ? "grossSalary-error" : undefined}
                            className={errors.grossSalary ? "input-error" : ""}
                            onChange={(e) => {
                                setForm({ ...form, grossSalary: e.target.value });
                                if (errors.grossSalary) {
                                    setFormErrors(prev => ({ ...prev, grossSalary: undefined }));
                                }
                            }}
                            data-testid="salary-input"
                            autoFocus
                        />
                        <span id="grossSalary-error" role="alert" className="error-text">
                            {errors.grossSalary || "\u00A0"}
                        </span>
                </div>

                <div className="form-group">
                        <label htmlFor="birthDate">Birth Date <span className="required">*</span></label>
                        <input
                            id="birthDate"
                            type="date"
                            name="birthDate"
                            value={form.birthDate}
                            placeholder="Birth Date"
                            aria-required="true"
                            aria-invalid={!!errors.birthDate}
                            aria-describedby={errors.birthDate ? "birthDate-error" : undefined}
                            className={errors.birthDate ? "input-error" : ""}
                            onChange={(e) =>
                            {
                                setForm({ ...form, birthDate: e.target.value });
                                if (errors.birthDate) {
                                    setFormErrors(prev => ({ ...prev, birthDate: undefined }));
                                }
                            }}
                            data-testid="birth-input"
                        />
                        <span id="birthDate-error" role="alert" className="error-text">
                            {errors.birthDate || "\u00A0"}
                        </span>
                </div>

                <button
                    id="submit"
                    className="form-submit"
                    type="submit"
                    name="calculate"
                    aria-label="Calculate salary benefits"
                    data-testid="calculate-button"
                >
                    Calculate
                </button>
            </form>
        </section>
    );
}
import "../styles/singleSelectCheckboxStyles.css"

export function SingleSelectCheckbox({label, options = [],value, onChange, isOptionDisabled = () => false}) {

    const handleChange = (option, disabled) => {
        if (disabled) return;
        onChange?.(option);
    };

    return (
        <div style={{ display: 'flex', alignItems: 'center' }}>
            <span style={{ margin: '5px' }}>{label}</span>

            {options.map((opt) => {
                const disabled = isOptionDisabled(opt);

                return (
                    <label
                        key={opt}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            cursor: disabled ? 'not-allowed' : 'pointer',
                            opacity: disabled ? 0.5 : 1
                        }}
                    >
                        {opt}:
                        <input
                            type="checkbox"
                            disabled={disabled}
                            checked={value === opt}
                            onChange={() => handleChange(opt, disabled)}
                        />
                    </label>
                );
            })}
        </div>
    );
}

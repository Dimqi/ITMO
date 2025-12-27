import { useRef } from "react";

export function useToast() {
    const toastRef = useRef(null);

    const showToast = (severity = "info", summary, detail ) => {
        toastRef.current?.show({
            severity,
            summary,
            detail,
            life: 3000
        });
    };

    return { toastRef, showToast };
}

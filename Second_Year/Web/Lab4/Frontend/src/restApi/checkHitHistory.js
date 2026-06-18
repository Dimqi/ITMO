export async function checkHitHistory() {
    const token = localStorage.getItem("token");
    const res = await fetch("http://localhost:8080/Lab4Web-1.0-SNAPSHOT/api/hit/history", {
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        }
    });

    if (res.status === 401) {
        throw new Error("Unauthorized");
    }

    if (!res.ok) {
        throw new Error("Ошибка сервера: " + res.status);
    }

    return await res.json();
}

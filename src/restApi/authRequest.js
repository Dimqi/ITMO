

export async function authRequest(mode, login, password){

    const endpointUrl =
        mode === "login"
            ? "/auth/login"
            : "/auth/register";

    const res = await fetch("http://localhost:8080/Lab4Web-1.0-SNAPSHOT/api"+endpointUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username: login, password })
    });

    if(res.ok) {
        return await res.json();
    } else {
        alert("Ошибка: " + res.status);
    }

}

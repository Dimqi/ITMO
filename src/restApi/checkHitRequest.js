export async function checkHit(x, y, r) {
    const token = localStorage.getItem("token")
    const endpointUrl = "/hit/checkHit";

    const res = await fetch("http://localhost:8080/Lab4Web-1.0-SNAPSHOT/api"+endpointUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({ x, y, r })
    });

    if(res.status === 401){
        throw new Error("Unauthorized");
    }

    if(res.ok) {
        return await res.json();
    } else {
        alert("Ошибка: " + res.status);
    }

}
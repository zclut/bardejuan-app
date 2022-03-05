import client from "./axios";

const tokenAuth = (token: string | null) => {
    if (token) {
        client.defaults.headers.common["Authorization"] = token;
        console.log(client.defaults.headers);
    } else {
        delete client.defaults.headers.common["Authorization"];
        console.log(client.defaults.headers);
    }
};

export default tokenAuth;
export class Utility {
    public static async fetchBuilder(url: string, method: string, headers: any | null, body: any | null) {
        return await fetch(url, {
            method,
            headers,
            body: body ? JSON.stringify(body) : null
        })
    }
}
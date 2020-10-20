export class Utility {
    public static async fetchBuilder(url: string, method: string, headers: any | null, body: any) {
        return await fetch(url, {
            method,
            headers,
            body: JSON.stringify(body)
        })
    }
}
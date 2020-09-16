export const getLorumIpsum = async (url: string) => {
  /*
        Post request options call with authorization
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        };
    */
  const requestOptions = {
    method: "GET",
  };

  // test comment

  return fetch(url, requestOptions).then((res) => res.json());
};

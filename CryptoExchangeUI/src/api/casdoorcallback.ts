const CASDOOR_ENDPOINT = "http://localhost:8000"; // 替换为你的 Casdoor 地址
const CLIENT_ID = "0c722340868bbc334971";               // 替换为 Application 的 Client ID
const CLIENT_SECRET = "33f987fd86fdf33ca6984a058c57d212df2d9212";       // 替换为 Client Secret

export async function CasdoorCallback(req: Request){
    POST: {
        const body = await req.json();

        const tokenRes = await fetch(`${CASDOOR_ENDPOINT}/api/login/oauth/access_token`, {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams({
            grant_type: "authorization_code",
            client_id: CLIENT_ID,
            client_secret: CLIENT_SECRET,
            code: body.code,
          }),
        });

        if (!tokenRes.ok) {
          console.error("Token exchange failed:", await tokenRes.text());
          return new Response("Failed to exchange token", { status: 500 });
        }

        const tokenData = await tokenRes.json();
        const accessToken = tokenData.access_token;

        const cookie = [
          `token=${accessToken}`,
          "Path=/",
          "HttpOnly",
          //"Secure",               // 移除这行可在 HTTP 下测试（不推荐生产）
          "SameSite=Strict",
          "Max-Age=3600"         // 1小时，单位秒
        ].join("; ");

        return new Response(
          JSON.stringify({ success: true, message: "Login successful" }),
          {
            status: 200,
            headers: {
              "Content-Type": "application/json",
              "Set-Cookie": cookie, // 浏览器会自动存储这个 Cookie
            // 如果跨域，还需：
              "Access-Control-Allow-Origin": "http://localhost:8888",
              "Access-Control-Allow-Credentials": "true"
          },
        }
      );
    }
}
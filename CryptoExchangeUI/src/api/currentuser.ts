import { jwtVerify, importX509 } from 'jose';

// 1. 使用完整的 X.509 证书（包含 -----BEGIN CERTIFICATE-----）
const CASDOOR_CERT = `-----BEGIN CERTIFICATE-----
MIIE3TCCAsWgAwIBAgIDAeJAMA0GCSqGSIb3DQEBCwUAMCgxDjAMBgNVBAoTBWFkbWlu
MRYwFAYDVQQDEw1jZXJ0LWJ1aWx0LWluMB4XDTI1MTIxMDA5MjczM1oXDTQ1MTIxMDA5
MjczM1owKDEOMAwGA1UEChMFYWRtaW4xFjAUBgNVBAMTDWNlcnQtYnVpbHQtaW4wggIi
MA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQDbQQeh+86vTz6vCfq28HmXWevR6l0J
yudqPoV0bHQd6CJ3dWBoQ7eLMzALQiv+qWLlFAQut+R3zQmqTjhi9MWIXUwTVZCXXxYE
APmbKzzQJVzVOgO0Rgdg+iQ5OekDawh+bCWkSGjBYYtdCBzrIpwRWAmk87Z+WEmlLZ9h
vYOik0YP7s9SxL7EAbFwsXPvFozh3degnKAgWTKnFAV0mduWugijXtke3CzIGlnQVGPc
PaJLJOjMdi18F7IjUDCoQw3LtARJGAarvrDNaow9cxvJ7iO1XXM+N2ShNcJpgIMsA2E9
shUGlans4M7LHzwuqSJQkdjzdWqj9Qad1PwkbOOEOKh3zIKW9hM5ErzZca+cZ6P0WGRF
qvKkeCuJzzWtQrtem7/H+aSqEW1A8iKBdA+kxSfJylPEL9WYCowgk8KeT8n87178fptH
65VZuf/YCysG+GWY65yIAOacO/HN1/Bh8oEqvpn+SN9I6LAEklbWkreOpVm/U2Zlz3Ov
PGhtSRMxkVjbxLzeZWEmrErWpfm9NL/gWcQiyjTqviIRMxpiY9EYUDfSKlPB7Wm7zlnW
Vh9H+DkpjBL2mJMrFbz/ehmye3DwxGT89tmBgcnXwC3sjWuzeJWNQV0AcaDX7hxkiTWR
jmhhz3qJ9d8QqGLHGJtSrdOe6QCkjDOmu2CKmafGmQIDAQABoxAwDjAMBgNVHRMBAf8E
AjAAMA0GCSqGSIb3DQEBCwUAA4ICAQBN5TJ89BqL5r4E0DFObR6AG+AA3v3Pb4l4On7c
oPs0ddUP6a9Fim51Vo7YvRaBVwcUWQr9c12HRA6Zz092zzP+he7PIqTJO5nvcWhwjQ/U
crnSeW7gkr8LddBQVi6nzEtgQQhtjTbPAWHujnpVRa/zUR05UiKhh5bWEZuVTARCB6iy
NNNH212TYu2z43lChrsYqs+96fdHBzel0JWQw/GBQK6VRwhfe4AdPZAmd2S4Cg17HJ9I
jmnKa0NFJ6kElYWQFlW3sRyAheFYVAdHZRVKc06OHOJ7GNuPwNQtVsQppHWkYcVTN/aD
BZER5aweprWXUdSIeSJz8PPc/E+ebyq5rRZbmyl+a0N3A7IbKmAydZIW4b43t43EUKjz
aDMKd76HFgtgxlPtgZ9pyH9nmloHTQPBhAyQGsjawkikczvtA4AfsAhCDwMkdlS56y0N
1/esIMtEKecCx+FWKpgDpHg9Uc6VTbriBXcT8uKoZcuA4FqVBbsFlNkiZtN+DoaFZJ+G
RV35sCehOc1LFvjnn5zkG7oWA0VvknJBFSJr92t7N5NMzPIWDnl0nCWkjUljqgNNwFPh
8+UcGCRim4+gQtvzKAJh9Pieaq3EitRUAx+Xx2UUYgF9D62Jm9s/Xqc0C7SFHKulMDWM
8RzursfeN/QTZd8CpuAA8lNScauEIdUlHg==
-----END CERTIFICATE-----`;

// 2. 将证书转换为公钥（用于验证 RS256）
const publicKey = await importX509(CASDOOR_CERT, 'RS256');

async function extractUserInfo(tokenContent:string){
    const { payload } = await jwtVerify(tokenContent, publicKey, {
      algorithms: ['RS256'],
    });

    return payload;
}

export async function GetCurrentUser(req: Request){
    GET:{
        const cookieHelper = req.headers.get("Cookie");
        const tokenContent = cookieHelper
        ?.split(";")
        .map(c => c.trim())
        .find(c => c.startsWith("token="))
        ?.substring(6);
        
        if(tokenContent){
            const payload = await extractUserInfo(tokenContent || "");
            const userInfo = {
                id: payload.sub,
                name: payload.name,
            };

            return new Response(
            JSON.stringify({ success: true, message: "Have logged in.", user: userInfo }),
            {
                status: 200,
            });
        } else {
            return new Response(
            JSON.stringify({ success: false, message: "No user logged in."}),
            {
                status: 403,
            });
        }
    }
}

export function LogoutCurrentUser(req:Request){
    GET:{
        const clearCookie = [
        "token=;",           // 值设为空
        "Path=/",
        "HttpOnly",
        //"Secure",            // 如果登录时用了 Secure，这里必须加
        "SameSite=Strict",   // 必须与设置时一致（也可能是 Lax）
        "Max-Age=0"          // 立即过期
        ].join("; ");

  return new Response(
    JSON.stringify({ success: true, message: "Logged out" }),
    {
      status: 200,
      headers: {
        "Content-Type": "application/json",
        "Set-Cookie": clearCookie,
        // 如果是跨域 SPA，还需 CORS：
        // "Access-Control-Allow-Origin": "http://localhost:5173",
        // "Access-Control-Allow-Credentials": "true"
      }
    }
  );
    }
}

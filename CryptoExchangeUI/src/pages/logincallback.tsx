import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export function LoginCallbackPage() {
  const navigate = useNavigate();

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');

    if (code) {
      // 发送 code 到你的后端换取 session
      fetch('/api/auth/casdoor/callback', {
        method: 'POST',
        credentials: 'include', // 用于设置 cookie
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ code })
      })
        .then(res => res.json())
        .then(data => {
          console.log(data);
          if (data.success) {
            navigate('/'); // 登录成功
          } else {
            navigate('/login?error=auth_failed');
          }
        });
    } else {
      navigate('/login');
    }
  }, [navigate]);

  return <div>Logging in...</div>;
}
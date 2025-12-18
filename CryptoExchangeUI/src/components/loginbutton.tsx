import { useEffect, useState } from "react";
import { getCasdoorAuthUrl} from "../utils/auth"

export function LoginButton() {
    const [isLoggedin, setIsLoggedin] = useState(false);
    const [userName, setUserName] = useState('');

    useEffect(() => {
              // 发送 code 到你的后端换取 session
              fetch('/api/me', {
                method: 'GET',
                credentials: 'include', 
              })
                .then(res => res.json())
                .then(data => {
                  console.log(data);
                  if (data.success) {
                    setUserName(data.user.name);
                    setIsLoggedin(true);
                  } else {
                    setUserName("");
                    setIsLoggedin(false);
                  }
                });
          }, [isLoggedin,userName]);

    const handleClick = () => {
        if(!isLoggedin) {
            window.location.href = getCasdoorAuthUrl();
        }
        else {
            setIsLoggedin(false);
            setUserName("");
            fetch('/api/logout', {
                method: 'GET',
                credentials: 'include', 
              })
                .then(res => res.json())
                .then(data => {
                  if (data.success) {
                    console.log("Logout success.");
                  } else {
                    console.log("Logout failed.");
                  }
                });
        }
    }

    console.log(isLoggedin);
    console.log(userName);

    return isLoggedin ? <button onClick={handleClick}>Log out {userName}</button>:<button onClick={handleClick}>Sign in with SSO</button>;
}
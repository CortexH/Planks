import api from './api'
import {useState, useEffect} from "react";
import App from "../../App.jsx";

function Auth({onlogin}) {
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [displayName, setDisplayName] = useState("");
    const [cpf, setCpf] = useState("");

    const handleRegister = async () => {
        try {
            await api.post("/user/new", {
                display_name: displayName,
                email: email,
                senha: senha,
                cpf: cpf,
            });
            alert("Usuário registrado");
        }catch(err) {
            alert("Erro ao registrar: " + err.response.data.erro);
        }
    }

    const handleLogin = async () => {
        try{
            //console.log(email, senha);
            const response = await api.post("/user/login", {
                email: email,
                senha: senha,
            })
            localStorage.setItem("TOKEN_AUTENTICAÇÃO", response.data.token);
            //onlogin();
            alert("Login bem sucedido");
        }catch(err) {
            alert(err.response.data.erro);
        }
    }

    return(
        <>
            <div className="login">
                <h2>Login</h2>
                <input type={"email"} placeholder={"Email"} value={email} onChange={(e) => setEmail(e.target.value)} />
                <input type={"password"} placeholder={"Senha"} value={senha} onChange={(e) => setSenha(e.target.value)} />

                <input type={"button"} value={"Logar"} onClick={() => handleLogin()} />
            </div>
            <div className="register">
                <h2>Register</h2>
                <input type={"email"} placeholder={"Email"} onChange={(e) => setEmail(e.target.value)} />
                <input type={"text"} placeholder={"cpf"} value={cpf} onChange={(e) => setCpf(e.target.value)} />
                <input type={"password"} placeholder={"Senha"} value={senha} onChange={(e) => setSenha(e.target.value)} />
                <input type={"text"} placeholder={"Nome"} onChange={(e) => setDisplayName(e.target.value)} />
                <input type={"button"} onClick={() => handleRegister()} />
            </div>
        </>
    )
}
export default Auth;


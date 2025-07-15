// src/components/auth/Login.tsx
import React, { useState } from "react";
import LoginForm from "../components/form/LoginForm";
import RegisterForm from "../components/form/RegisterForm";

const Login: React.FC = () => {
  const [currentView, setCurrentView] = useState<"login" | "register">("login");
  const [loading, setLoading] = useState(false);

  const handleLogin = (values: any) => {
    setLoading(true);
    console.log("Login:", values);
    setTimeout(() => {
      setLoading(false);
    }, 1500);
  };

  const handleRegister = (values: any) => {
    setLoading(true);
    console.log("Register:", values);
    setTimeout(() => {
      setLoading(false);
      setCurrentView("login");
    }, 1500);
  };

  return (
    <>
      {currentView === "login" ? (
        <LoginForm
          onRegisterClick={() => setCurrentView("register")}
          loading={loading}
          onLogin={handleLogin}
        />
      ) : (
        <RegisterForm
          onLoginClick={() => setCurrentView("login")}
          loading={loading}
          onRegister={handleRegister}
        />
      )}
    </>
  );
};

export default Login;

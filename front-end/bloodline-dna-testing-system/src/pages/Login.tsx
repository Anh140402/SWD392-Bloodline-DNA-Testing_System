import React, { useState } from "react";
// import { useNavigate } from "react-router-dom";
import { message } from "antd";

import LoginForm from "../components/form/LoginForm";
import RegisterForm from "../components/form/RegisterForm";
import { userLogin } from "../api/testOrder";
import axios from "axios";

const Login: React.FC = () => {
  const [currentView, setCurrentView] = useState<"login" | "register">("login");
  const [loading, setLoading] = useState(false);
  // const navigate = useNavigate();

  const handleLogin = async (values: { username: string; password: string }) => {
    setLoading(true);
    try {
      const response = await axios.post("http://backend-server.com/auth/login", {
        username: values.username,
        password: values.password,
      });

      const data = response.data;
      // Lưu thông tin user/token tùy API trả về
      localStorage.setItem("username", data.username);
      // localStorage.setItem("token", data.token);

      message.success("Login successful!");
    } catch (error: any) {
      message.error(error?.response?.data?.message || "Login failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async (values: any) => {
    setLoading(true);
    try {
      // await userRegister(values); // Gọi API đăng ký, bạn tự viết hàm userRegister
      message.success("Register successful! Please login.");
      setCurrentView("login");
    } catch (error: any) {
      message.error(error?.message || "Register failed. Please try again.");
    } finally {
      setLoading(false);
    }
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

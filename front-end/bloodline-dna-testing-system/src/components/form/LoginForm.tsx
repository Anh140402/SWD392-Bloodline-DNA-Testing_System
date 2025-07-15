// src/components/auth/LoginForm.tsx
import React from "react";
import { Button, Form, Input, Typography, message } from "antd";
import { MailOutlined, LockOutlined } from "@ant-design/icons";

const { Title, Text, Paragraph } = Typography;

const LoginForm: React.FC<{
  onRegisterClick: () => void;
  loading: boolean;
  onLogin: (values: any) => void;
}> = ({ onRegisterClick, loading, onLogin }) => {
  return (
    <div className="min-h-screen bg-white flex">
      {/* Left side - Image */}
      <div className="w-1/2 relative overflow-hidden">
        <img
          src="https://readdy.ai/api/search-image?query=modern%20minimalist%20digital%20workspace%20with%20clean%20geometric%20patterns%20and%20soft%20gradient%20lighting%20featuring%20blue%20and%20purple%20tones%20creating%20a%20professional%20technology%20environment%20with%20abstract%20floating%20elements%20and%20smooth%20surfaces%20perfect%20for%20login%20interface%20design&width=720&height=1024&seq=login-hero-001&orientation=portrait"
          alt="Login Background"
          className="w-full h-full object-cover object-top"
        />
        <div className="absolute  inset-0 bg-gradient-to-r from-blue-600/20 to-purple-600/20"></div>
        <div className="absolute inset-0 flex justify-center">
          <div className="text-center text-white p-8 mt-[10%]">
            <Title level={1} className="text-white mb-4">
              Welcome Back
            </Title>
            <Paragraph className="text-white/90 text-xl max-w-md">
              Access your account and continue your journey with our platform
            </Paragraph>
          </div>
        </div>
      </div>

      {/* Right side - Form */}
      <div className="w-1/2 flex  justify-center p-12 bg-gray-50">
        <div className="max-w-md w-full space-y-8">
          <div className="text-center mb-8">
            <div className="mx-auto w-16 h-16 bg-gradient-to-r from-blue-600 to-purple-600 rounded-2xl flex justify-center mb-6">
              <i className="fas fa-lock text-2xl text-white"></i>
            </div>
            <Title level={2} className="text-gray-800 mb-2">
              Sign In
            </Title>
            <Paragraph className="text-gray-600">
              Enter your credentials to access your account
            </Paragraph>
          </div>

          <Form onFinish={onLogin} layout="vertical" className="space-y-6">
            <Form.Item
              name="email"
              label="Email Address"
              rules={[
                { required: true, message: "Please enter your email" },
                { type: "email", message: "Please enter a valid email" },
              ]}
            >
              <Input
                prefix={<MailOutlined />}
                placeholder="Enter your email"
                size="large"
                className="rounded-xl"
              />
            </Form.Item>

            <Form.Item
              name="password"
              label="Password"
              rules={[
                { required: true, message: "Please enter your password" },
              ]}
            >
              <Input.Password
                prefix={<LockOutlined />}
                placeholder="Enter your password"
                size="large"
                className="rounded-xl"
              />
            </Form.Item>

            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                loading={loading}
                className="w-full h-12 bg-gradient-to-r from-blue-600 to-purple-600 border-none text-lg rounded-xl"
              >
                Sign In
              </Button>
            </Form.Item>
          </Form>

          <div className="text-center">
            <Text className="text-gray-600">Don't have an account?</Text>
            <Button type="link" onClick={onRegisterClick}>
              Sign Up
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;

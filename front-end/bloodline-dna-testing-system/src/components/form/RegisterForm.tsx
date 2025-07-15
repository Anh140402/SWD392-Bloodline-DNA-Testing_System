// src/components/auth/RegisterForm.tsx
import React from "react";
import { Button, Form, Input, Typography, Radio, DatePicker, message } from "antd";
import { UserOutlined, MailOutlined, LockOutlined, PhoneOutlined } from "@ant-design/icons";

const { Title , Text } = Typography;

const RegisterForm: React.FC<{ onLoginClick: () => void; loading: boolean; onRegister: (values: any) => void }> = ({
  onLoginClick,
  loading,
  onRegister,
}) => {
  return (
    <div className="min-h-screen bg-white flex">
      {/* Left side - Form */}
      <div className="w-1/2 p-12 flex justify-center bg-gray-50 overflow-y-auto">
        <div className="max-w-lg w-full">
          <Title level={2} className="text-center mb-6">Create Account</Title>

          <Form onFinish={onRegister} layout="vertical">
            <Form.Item name="username" label="Username" rules={[{ required: true }]}>
              <Input prefix={<UserOutlined />} size="large" />
            </Form.Item>

            <Form.Item name="email" label="Email" rules={[{ required: true, type: "email" }]}>
              <Input prefix={<MailOutlined />} size="large" />
            </Form.Item>

            <Form.Item name="password" label="Password" rules={[{ required: true }]}>
              <Input.Password prefix={<LockOutlined />} size="large" />
            </Form.Item>

            <Form.Item name="phone" label="Phone" rules={[{ required: true }]}>
              <Input prefix={<PhoneOutlined />} size="large" />
            </Form.Item>

            <Form.Item name="gender" label="Gender" rules={[{ required: true }]}>
              <Radio.Group>
                <Radio value="MALE">Male</Radio>
                <Radio value="FEMALE">Female</Radio>
                <Radio value="OTHER">Other</Radio>
              </Radio.Group>
            </Form.Item>

            <Form.Item name="dateOfBirth" label="Date of Birth" rules={[{ required: true }]}>
              <DatePicker className="w-full" size="large" />
            </Form.Item>

            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                loading={loading}
                className="w-full h-12 bg-gradient-to-r from-purple-600 to-blue-600 border-none text-lg rounded-xl"
              >
                Register
              </Button>
            </Form.Item>
          </Form>

          <div className="text-center mt-4">
            <Text>Already have an account?</Text>
            <Button type="link" onClick={onLoginClick}>
              Sign In
            </Button>
          </div>
        </div>
      </div>

      {/* Right side - Image */}
      <div className="w-1/2 relative overflow-hidden">
        <img
          src="https://readdy.ai/api/search-image?query=modern%20creative%20workspace%20illustration%20with%20vibrant%20purple%20and%20blue%20gradients%20showing%20digital%20innovation%20and%20technology%20concepts%20with%20abstract%20geometric%20shapes%20and%20floating%20elements%20representing%20user%20registration%20and%20account%20creation%20in%20a%20professional%20clean%20environment&width=720&height=1024&seq=register-hero-002&orientation=portrait"
          alt="Register Background"
          className="w-full h-full object-cover object-top"
        />
      </div>
    </div>
  );
};

export default RegisterForm;

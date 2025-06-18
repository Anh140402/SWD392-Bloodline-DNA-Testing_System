// src/components/HeaderComponent.tsx
import React from "react";
import { Layout, Button } from "antd"; // Chỉ import những gì cần thiết

const { Header } = Layout;

const HeaderComponent: React.FC = () => {
  return (
    <Header
      className="ant-layout-header fixed top-0 left-0 w-full z-50 !bg-[#0063EC] p-0 h-20 shadow-md"
    >
      <div className="container mx-auto px-3 h-full">
        <div className="flex justify-between items-center h-full">
          <div className="flex items-center">
            <h1 className="text-xl font-bold m-0">
              <span className="text-white font-bold text-2xl flex items-center gap-2">
                <img src="adnlogo2.jpg" width={35} alt="AND TEST Logo" />
                AND TEST
              </span>
            </h1>
          </div>

          <div className="flex items-center space-x-6">
            <a
              href="#"
              className="!text-white font-bold text-2xl cursor-pointer hover:text-gray-300"
            >
              Home
            </a>
            <a
              href="#"
              className="!text-white font-bold text-2xl cursor-pointer hover:text-gray-300"
            >
              Services
            </a>
            <a
              href="#"
              className="!text-white font-bold text-2xl cursor-pointer hover:text-gray-300"
            >
              About
            </a>
            <a
              href="#"
              className="!text-white font-bold text-2xl cursor-pointer hover:text-gray-300"
            >
              Contact
            </a>
          </div>
          <div>
            <Button
              type="primary"
              className="!rounded-button whitespace-nowrap cursor-pointer"
            >
              Login
            </Button>
          </div>
        </div>
      </div>
    </Header>
  );
};

export default HeaderComponent;
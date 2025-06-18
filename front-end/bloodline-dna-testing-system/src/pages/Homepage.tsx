// src/pages/HomePage.tsx (hoặc src/HomePage.tsx)
import React from "react";
import { Layout } from "antd";import HeaderComponent from "../components/Header";
import MainContentComponent from "../components/MainContent";
import FooterComponent from "../components/Footer";
const HomePage: React.FC = () => {
  return (
    <Layout className="min-h-screen">
      <HeaderComponent />
      <MainContentComponent /> {/* MainContentComponent bao gồm cả Content và Hero/Search sections */}
      <FooterComponent />
    </Layout>
  );
};

export default HomePage;
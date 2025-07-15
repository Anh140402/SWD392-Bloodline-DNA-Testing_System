// src/pages/HomePage.tsx (hoặc src/HomePage.tsx)
import React, { useEffect } from "react";
import { Layout } from "antd";import HeaderComponent from "../components/Header";
import MainContentComponent from "../components/MainContent";
import FooterComponent from "../components/Footer";
import { GetAllUsers } from "../api/testOrder";
// import { GetAllTestOrders } from "../api/testOrder";

const HomePage: React.FC = () => {
  const [users, setUsers] = React.useState([]);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await GetAllUsers();
        setUsers(response.data);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };

    fetchUsers();
  }, []);
  console.log("Users:", users); // Kiểm tra dữ liệu người dùng

  return (
    <Layout className="min-h-screen">
      <HeaderComponent />
      <MainContentComponent /> {/* MainContentComponent bao gồm cả Content và Hero/Search sections */}
      <FooterComponent />
    </Layout>
  );
};

export default HomePage;
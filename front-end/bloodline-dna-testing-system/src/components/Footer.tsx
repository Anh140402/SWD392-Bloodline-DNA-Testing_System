// src/components/FooterComponent.tsx
import React from "react";
import { Layout, Typography, Divider, Space } from "antd";

const { Footer } = Layout;
const { Title, Paragraph, Text } = Typography;

const FooterComponent: React.FC = () => {
  return (
    <Footer className="bg-gray-800 text-white py-12">
      <div className="container mx-auto px-4">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          <div>
            <Title level={4} className="!text-white mb-4"> {/* Added ! to override antd */}
              dnakhaiainh.com
            </Title>
            <Paragraph className="text-gray-400">
              Providing quality healthcare services since 2010. Our mission is
              to improve the health and well-being of our community.
            </Paragraph>
            <div className="flex space-x-4 mt-4">
              <a
                href="#"
                className="text-gray-400 hover:text-white cursor-pointer"
              >
                <i className="fab fa-facebook-f"></i>
              </a>
              <a
                href="#"
                className="text-gray-400 hover:text-white cursor-pointer"
              >
                <i className="fab fa-twitter"></i>
              </a>
              <a
                href="#"
                className="text-gray-400 hover:text-white cursor-pointer"
              >
                <i className="fab fa-instagram"></i>
              </a>
              <a
                href="#"
                className="text-gray-400 hover:text-white cursor-pointer"
              >
                <i className="fab fa-linkedin-in"></i>
              </a>
            </div>
          </div>
          <div>
            <Title level={4} className="!text-white mb-4">
              Services
            </Title>
            <ul className="space-y-2">
              <li>
                <a
                  href="#"
                  className="text-gray-400 hover:text-white cursor-pointer"
                >
                  Health Check-ups
                </a>
              </li>
              <li>
                <a
                  href="#"
                  className="text-gray-400 hover:text-white cursor-pointer"
                >
                  Cardiology
                </a>
              </li>
              <li>
                <a
                  href="#"
                  className="text-gray-400 hover:text-white cursor-pointer"
                >
                  Neurology
                </a>
              </li>
              <li>
                <a
                  href="#"
                  className="text-gray-400 hover:text-white cursor-pointer"
                >
                  Orthopedics
                </a>
              </li>
              <li>
                <a
                  href="#"
                  className="text-gray-400 hover:text-white cursor-pointer"
                >
                  Pediatrics
                </a>
              </li>
            </ul>
          </div>
          <div>
            <Title level={4} className="!text-white mb-4">
              Quick Links
            </Title>
            <ul className="space-y-2">
              <li>
                <a
                  href="#"
                  className="text-gray-400 hover:text-white cursor-pointer"
                >
                  About Us
                </a>
              </li>
              <li>
                <a
                  href="#"
                  className="text-gray-400 hover:text-white cursor-pointer"
                >
                  Doctors
                </a>
              </li>
              <li>
                <a
                  href="#"
                  className="text-gray-400 hover:text-white cursor-pointer"
                >
                  Facilities
                </a>
              </li>
              <li>
                <a
                  href="#"
                  className="text-gray-400 hover:text-white cursor-pointer"
                >
                  Appointments
                </a>
              </li>
              <li>
                <a
                  href="#"
                  className="text-gray-400 hover:text-white cursor-pointer"
                >
                  Contact
                </a>
              </li>
            </ul>
          </div>
          <div>
            <Title level={4} className="!text-white mb-4">
              Contact Us
            </Title>
            <ul className="space-y-2">
              <li className="flex items-start">
                <i className="fas fa-map-marker-alt text-gray-400 mr-3 mt-1"></i>
                <span className="text-gray-400">
                  123 Medical Center Drive, Healthcare City, HC 12345
                </span>
              </li>
              <li className="flex items-start">
                <i className="fas fa-phone-alt text-gray-400 mr-3 mt-1"></i>
                <span className="text-gray-400">+1 (555) 123-4567</span>
              </li>
              <li className="flex items-start">
                <i className="fas fa-envelope text-gray-400 mr-3 mt-1"></i>
                <span className="text-gray-400">info@dnakhaiainh.com</span>
              </li>
            </ul>
            <div className="mt-4">
              <div className="flex items-center space-x-2">
                <i className="fab fa-cc-visa text-2xl text-gray-400"></i>
                <i className="fab fa-cc-mastercard text-2xl text-gray-400"></i>
                <i className="fab fa-cc-paypal text-2xl text-gray-400"></i>
              </div>
            </div>
          </div>
        </div>
        <Divider className="border-gray-700 my-8" />
        <div className="flex flex-col md:flex-row justify-between items-center">
          <Text className="text-gray-400">
            © 2025 dnakhaiainh.com. All rights reserved.
          </Text>
          <div className="mt-4 md:mt-0">
            <a
              href="#"
              className="text-gray-400 hover:text-white mr-6 cursor-pointer"
            >
              Privacy Policy
            </a>
            <a
              href="#"
              className="text-gray-400 hover:text-white mr-6 cursor-pointer"
            >
              Terms of Service
            </a>
            <a
              href="#"
              className="text-gray-400 hover:text-white cursor-pointer"
            >
              Sitemap
            </a>
          </div>
        </div>
      </div>
    </Footer>
  );
};

export default FooterComponent;
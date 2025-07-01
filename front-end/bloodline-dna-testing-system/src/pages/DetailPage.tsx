// The exported code uses Tailwind CSS. Install Tailwind CSS in your dev environment to ensure all styles work.
import React, { useState } from "react";
import { Button, Card, Steps, Typography, Tag } from "antd";
import {
  ArrowLeftOutlined,
  CalendarOutlined,
  ClockCircleOutlined,
  LoadingOutlined,
  SmileOutlined,
} from "@ant-design/icons";
const { Title, Text } = Typography;
const DetailPage: React.FC = () => {
  const [isAccepted, setIsAccepted] = useState(false);
  const handleAccept = () => {
    setIsAccepted(true);
  };
  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-50 to-gray-50">
      <header className="bg-white shadow-md ">
        <div className="container mx-auto px-6 py-5 relative">
          <div className="absolute left-6 top-1/2 -translate-y-1/2">
            <Button
              type="text"
              icon={<ArrowLeftOutlined />}
              className="!rounded-button cursor-pointer whitespace-nowrap"
              onClick={() => console.log("Navigate back")}
            >
              Back
            </Button>
          </div>

          {/* Title centered */}
          <div className="flex justify-center">
            <div className="text-xl font-semibold bg-gradient-to-r from-blue-600 to-blue-800 bg-clip-text text-transparent">
              Laboratory Management System
            </div>
          </div>
        </div>
      </header>

      <div className="container mx-auto px-4 py-6">
        <div className="grid grid-cols-1 gap-6">
          <Card className="mb-6 shadow-md hover:shadow-lg transition-shadow duration-300 rounded-xl border-none bg-white/90">
            <div className="flex gap-5 items-center mb-4">
              <Title level={5} className="text-blue-800">
                Appointment Progress
              </Title>
              <Tag
                color={isAccepted ? "green" : "orange"}
                className="ml-4 px-3 py-1"
              >
                {isAccepted ? "Accepted" : "Pending"}
              </Tag>
            </div>
            <Steps
              current={isAccepted ? 2 : 1}
              className="mt-4"
              items={[
                {
                  title: "Appointment Created",
                  description: "June 27, 02:30 PM",
                  status: "finish",
                },
                {
                  title: "Sample Collection",
                  description: "June 28, 09:15 AM",
                  status: "finish",
                  icon: <LoadingOutlined />,
                },
                {
                  title: "Processing",
                  description: "",
                  status: "process",
                },
                {
                  title: "Analysis",
                  description: "",
                  status: "error",
                },
                {
                  title: "Results",
                  description: "",
                  status: "wait",
                  icon: <SmileOutlined />,
                },
              ]}
            />
          </Card>
          <div>
            {/* Test Information Card */}
            <Card className="mb-6 shadow-md hover:shadow-lg transition-shadow duration-300 rounded-xl border-none">
              <div className="flex justify-between items-start mb-4">
                <Title level={4} className="mb-0 text-blue-800">
                  Test Information
                </Title>
                <div>
                  <Text className="text-gray-500 mr-2">Test ID:</Text>
                  <Text strong>T004</Text>
                </div>
              </div>
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div>
                  <Text className="text-gray-500 block">Test Type</Text>
                  <Text strong className="text-lg">
                    Lipid Panel
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">Priority</Text>
                  <Tag color="orange" className="mt-1">
                    Medium
                  </Tag>
                </div>
                <div>
                  <Text className="text-gray-500 block">Test Date</Text>
                  <Text strong className="text-lg">
                    June 28, 2025
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">Result Date</Text>
                  <Text strong className="text-lg">
                    Pending
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">
                    Requesting Physician
                  </Text>
                  <Text strong className="text-lg">
                    Dr. Maria Rodriguez
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">Department</Text>
                  <Text strong className="text-lg">
                    Cardiology
                  </Text>
                </div>
              </div>
            </Card>
            {/* Patient Information */}
            <Card className="mb-6 shadow-md hover:shadow-lg transition-shadow duration-300 rounded-xl border-none bg-white/90">
              <Title level={4} className="text-blue-800">
                Patient Information
              </Title>
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div>
                  <Text className="text-gray-500 block">Patient Name</Text>
                  <Text strong className="text-lg">
                    Sarah Williams
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">Patient ID</Text>
                  <Text strong className="text-lg">
                    P10045
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">Date of Birth</Text>
                  <Text strong className="text-lg">
                    May 12, 1985
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">Gender</Text>
                  <Text strong className="text-lg">
                    Female
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">Contact</Text>
                  <Text strong className="text-lg">
                    +1 (555) 123-4567
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">Email</Text>
                  <Text strong className="text-lg">
                    sarah.williams@example.com
                  </Text>
                </div>
              </div>
            </Card>
            {/* Sample Details */}
            <Card className="mb-6 shadow-md hover:shadow-lg transition-shadow duration-300 rounded-xl border-none bg-white/90">
              <Title level={4} className="text-blue-800">
                Sample Details
              </Title>
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div>
                  <Text className="text-gray-500 block">Collection Method</Text>
                  <Text strong className="text-lg">
                    Venipuncture
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">Sample Type</Text>
                  <Text strong className="text-lg">
                    Blood Serum
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">
                    Collection Date & Time
                  </Text>
                  <div className="flex items-center">
                    <CalendarOutlined className="mr-1 text-gray-500" />
                    <Text strong className="text-lg">
                      June 28, 2025
                    </Text>
                    <ClockCircleOutlined className="ml-3 mr-1 text-gray-500" />
                    <Text strong className="text-lg">
                      09:15 AM
                    </Text>
                  </div>
                </div>
                <div>
                  <Text className="text-gray-500 block">Collected By</Text>
                  <Text strong className="text-lg">
                    Nurse Jennifer Adams
                  </Text>
                </div>
                <div>
                  <Text className="text-gray-500 block">Sample Status</Text>
                  <Tag color="blue" className="mt-1">
                    Processing
                  </Tag>
                </div>
                <div>
                  <Text className="text-gray-500 block">Sample Quantity</Text>
                  <Text strong className="text-lg">
                    10 mL
                  </Text>
                </div>
              </div>
            </Card>
            {/* Accept Button - Centered */}
            <div className="flex justify-center my-8">
              <Button
                type="primary"
                size="large"
                className="!rounded-button cursor-pointer whitespace-nowrap px-12 py-6 flex items-center text-lg bg-gradient-to-r from-blue-600 to-blue-800 hover:from-blue-700 hover:to-blue-900 border-none shadow-lg hover:shadow-xl transition-all duration-300"
                onClick={handleAccept}
                disabled={isAccepted}
              >
                {isAccepted ? "Appointment Accepted" : "Accept Appointment"}
              </Button>
            </div>
          </div>
        </div>
      </div>
      {/* Footer with subtle branding */}
      <footer className="bg-gradient-to-b from-white to-gray-50 border-t border-gray-200 py-6 mt-8">
        <div className="container mx-auto px-4 text-center text-gray-600 text-sm">
          <p className="font-medium">
            © 2025 Laboratory Management System. All rights reserved.
          </p>
        </div>
      </footer>
    </div>
  );
};
export default DetailPage;

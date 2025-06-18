// src/components/MainContentComponent.tsx
import React from "react";
import {
  Layout,
  Typography,
  Button,
  Input,
  Card,
  Row,
  Col,
  Space,
} from "antd";
import { SearchOutlined, RightOutlined } from "@ant-design/icons";
import * as echarts from "echarts";

const { Content } = Layout; // Chỉ Layout.Content
const { Title, Paragraph, Text } = Typography;

const MainContentComponent: React.FC = () => {
  React.useEffect(() => {
    const chartDom = document.getElementById("serviceChart");
    if (chartDom) {
      const myChart = echarts.init(chartDom);
      const option = {
        animation: false,
        radar: {
          indicator: [
            { name: "Quality", max: 100 },
            { name: "Service", max: 100 },
            { name: "Speed", max: 100 },
            { name: "Price", max: 100 },
            { name: "Satisfaction", max: 100 },
          ],
          radius: 80,
        },
        series: [
          {
            type: "radar",
            data: [
              {
                value: [95, 90, 85, 88, 92],
                name: "Our Service",
                areaStyle: {
                  color: "rgba(64, 158, 255, 0.6)",
                },
                lineStyle: {
                  color: "#409EFF",
                },
              },
            ],
          },
        ],
      };
      myChart.setOption(option);
      // Clean up chart on component unmount
      return () => {
        myChart.dispose();
      };
    }
  }, []);

  return (
    <>
      {/* Hero Section - Added padding-top to account for fixed header height (h-20) */}
      <div className="relative pt-20">
        <div className="absolute inset-0 bg-gradient-to-r from-blue-900 to-transparent z-0">
          <img
            src="https://readdy.ai/api/search-image?query=A%20professional%20modern%20healthcare%20facility%20with%20clean%20and%20bright%20environment%2C%20medical%20professionals%20in%20white%20coats%20attending%20to%20patients%2C%20showing%20a%20welcoming%20atmosphere%20with%20soft%20lighting%20and%20modern%20medical%20equipment%2C%20high%20quality%20professional%20photography&width=1440&height=500&seq=1&orientation=landscape"
            alt="Healthcare services"
            className="w-full h-full object-cover object-top opacity-80"
          />
        </div>
        <div className="container mx-auto px-4 py-16 relative z-10">
          <div className="flex flex-col md:flex-row items-center">
            <div className="md:w-1/2 text-white">
              <Title level={1} className="!text-white text-4xl font-bold mb-6"> {/* Added ! */}
                Professional Healthcare Services
              </Title>
              <Paragraph className="text-white text-lg mb-8">
                We provide comprehensive healthcare solutions with a focus on
                quality and patient care. Our team of experienced professionals
                is dedicated to your well-being.
              </Paragraph>
              <div className="flex space-x-4">
                <Button
                  type="primary"
                  size="large"
                  className="!bg-[#96D6FF]" // Tailwind class might be better: bg-blue-300
                >
                  Get Started
                </Button>
                <Button
                  ghost
                  size="large"
                  className="text-white border-white hover:text-blue-300 hover:border-blue-300 !rounded-button whitespace-nowrap cursor-pointer"
                >
                  Learn More
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Search Section */}
      <div className="bg-white py-6 shadow-md">
        <div className="container mx-auto px-4">
          <div className="flex items-center justify-center">
            <div className="w-full max-w-2xl">
              <Input
                size="large"
                placeholder="Search for services..."
                prefix={<SearchOutlined className="text-gray-400" />}
                suffix={
                  <Button
                    type="primary"
                    className="!rounded-button whitespace-nowrap cursor-pointer"
                  >
                    Search
                  </Button>
                }
                className="rounded-lg"
              />
            </div>
          </div>
        </div>
      </div>

      {/* Main Content Area */}
      <Content className="bg-gray-50 py-12">
        <div className="container mx-auto px-4">
          {/* Promotion Section */}
          <div className="mb-16 bg-gradient-to-r from-blue-500 to-blue-300 rounded-lg overflow-hidden">
            <div className="flex flex-col md:flex-row">
              <div className="md:w-1/2 p-8 flex flex-col justify-center">
                <div className="bg-yellow-400 text-blue-900 inline-block px-3 py-1 rounded-full text-sm font-bold mb-4">
                  NEW OFFER
                </div>
                <Title level={2} className="!text-white text-3xl font-bold mb-4">
                  EXPERIENCE 100% QUALITY TREATMENT
                </Title>
                <Paragraph className="text-white mb-6">
                  Limited time offer for new patients. Book your appointment
                  today and receive a comprehensive health check-up.
                </Paragraph>
                <Button
                  type="default"
                  size="large"
                  className="bg-white text-blue-600 hover:bg-gray-100 !rounded-button whitespace-nowrap cursor-pointer w-40"
                >
                  Book Now
                </Button>
              </div>
              <div className="md:w-1/2">
                <img
                  src="https://readdy.ai/api/search-image?query=A%20happy%20doctor%20and%20patient%20smiling%20together%20in%20a%20modern%20medical%20office%2C%20professional%20healthcare%20consultation%2C%20bright%20clean%20environment%20with%20medical%20equipment%20visible%20in%20background%2C%20high%20quality%20professional%20photography&width=600&height=400&seq=2&orientation=landscape"
                  alt="Doctor and patient"
                  className="w-full h-full object-cover object-top"
                />
              </div>
            </div>
          </div>

          {/* Services Section */}
          <div className="mb-16">
            <div className="text-center mb-10">
              <Title level={2} className="text-3xl font-bold">
                Our Healthcare Services
              </Title>
              <Paragraph className="text-gray-600 max-w-2xl mx-auto">
                We offer a wide range of medical services to meet your
                healthcare needs. Our team of specialists is committed to
                providing the highest quality care.
              </Paragraph>
            </div>

            <div className="mb-12">
              <Card className="shadow-md">
                <div className="flex flex-col md:flex-row">
                  <div className="md:w-2/3 p-6">
                    <Title level={3} className="text-xl font-bold mb-4">
                      Comprehensive Health Check-ups
                    </Title>
                    <Paragraph className="text-gray-600 mb-4">
                      Our comprehensive health check-ups are designed to assess
                      your overall health status and identify potential health
                      issues before they become serious. Each check-up includes
                      a thorough physical examination, blood tests, and
                      specialized screenings based on your age, gender, and risk
                      factors.
                    </Paragraph>
                    <Paragraph className="text-gray-600 mb-4">
                      Regular health check-ups are essential for early detection
                      and prevention of diseases. Our experienced medical team
                      will provide you with detailed results and personalized
                      recommendations to improve your health and well-being.
                    </Paragraph>
                    <Button
                      type="primary"
                      className="!rounded-button whitespace-nowrap cursor-pointer"
                    >
                      Learn More <RightOutlined />
                    </Button>
                  </div>
                  <div className="md:w-1/3">
                    <img
                      src="https://readdy.ai/api/search-image?query=A%20modern%20medical%20examination%20room%20with%20advanced%20diagnostic%20equipment%2C%20clean%20and%20professional%20environment%2C%20medical%20charts%20visible%2C%20bright%20lighting%20and%20organized%20medical%20tools%2C%20high%20quality%20professional%20photography&width=400&height=300&seq=3&orientation=landscape"
                      alt="Health Check-up"
                      className="w-full h-full object-cover object-top"
                    />
                  </div>
                </div>
              </Card>
            </div>

            {/* Service Features Grid */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-12">
              <div className="text-center">
                <div className="bg-gray-100 rounded-full p-4 w-24 h-24 mx-auto mb-4 flex items-center justify-center">
                  <i className="fas fa-heartbeat text-3xl text-blue-500"></i>
                </div>
                <Text strong className="block">
                  Cardiology
                </Text>
              </div>
              <div className="text-center">
                <div className="bg-gray-100 rounded-full p-4 w-24 h-24 mx-auto mb-4 flex items-center justify-center">
                  <i className="fas fa-brain text-3xl text-blue-500"></i>
                </div>
                <Text strong className="block">
                  Neurology
                </Text>
              </div>
              <div className="text-center">
                <div className="bg-gray-100 rounded-full p-4 w-24 h-24 mx-auto mb-4 flex items-center justify-center">
                  <i className="fas fa-tooth text-3xl text-blue-500"></i>
                </div>
                <Text strong className="block">
                  Dental Care
                </Text>
              </div>
              <div className="text-center">
                <div className="bg-gray-100 rounded-full p-4 w-24 h-24 mx-auto mb-4 flex items-center justify-center">
                  <i className="fas fa-eye text-3xl text-blue-500"></i>
                </div>
                <Text strong className="block">
                  Ophthalmology
                </Text>
              </div>
              <div className="text-center">
                <div className="bg-gray-100 rounded-full p-4 w-24 h-24 mx-auto mb-4 flex items-center justify-center">
                  <i className="fas fa-bone text-3xl text-blue-500"></i>
                </div>
                <Text strong className="block">
                  Orthopedics
                </Text>
              </div>
              <div className="text-center">
                <div className="bg-gray-100 rounded-full p-4 w-24 h-24 mx-auto mb-4 flex items-center justify-center">
                  <i className="fas fa-lungs text-3xl text-blue-500"></i>
                </div>
                <Text strong className="block">
                  Pulmonology
                </Text>
              </div>
              <div className="text-center">
                <div className="bg-gray-100 rounded-full p-4 w-24 h-24 mx-auto mb-4 flex items-center justify-center">
                  <i className="fas fa-baby text-3xl text-blue-500"></i>
                </div>
                <Text strong className="block">
                  Pediatrics
                </Text>
              </div>
              <div className="text-center">
                <div className="bg-gray-100 rounded-full p-4 w-24 h-24 mx-auto mb-4 flex items-center justify-center">
                  <i className="fas fa-stethoscope text-3xl text-blue-500"></i>
                </div>
                <Text strong className="block">
                  General Medicine
                </Text>
              </div>
            </div>
          </div>

          {/* Facility Section */}
          <div className="mb-16">
            <div className="text-center mb-10">
              <Title level={2} className="text-3xl font-bold">
                Our Modern Facilities
              </Title>
              <Paragraph className="text-gray-600 max-w-2xl mx-auto">
                We are equipped with state-of-the-art medical facilities and
                technology to provide the best care for our patients.
              </Paragraph>
            </div>

            <Row gutter={[24, 24]}>
              <Col xs={24} md={12}>
                <Card className="h-full shadow-md overflow-hidden">
                  <img
                    src="https://readdy.ai/api/search-image?query=A%20modern%20hospital%20ward%20with%20advanced%20medical%20equipment%2C%20clean%20beds%2C%20bright%20environment%20with%20natural%20light%20coming%20through%20windows%2C%20professional%20healthcare%20setting%20with%20monitoring%20devices%2C%20high%20quality%20professional%20photography&width=600&height=400&seq=4&orientation=landscape"
                    alt="Modern Hospital Ward"
                    className="w-full h-64 object-cover object-top"
                  />
                  <div className="p-6">
                    <Title level={4} className="mb-2">
                      Advanced Treatment Rooms
                    </Title>
                    <Paragraph className="text-gray-600">
                      Our treatment rooms are equipped with the latest medical
                      technology to provide efficient and effective care for all
                      patients.
                    </Paragraph>
                  </div>
                </Card>
              </Col>
              <Col xs={24} md={12}>
                <Card className="h-full shadow-md overflow-hidden">
                  <img
                    src="https://readdy.ai/api/search-image?query=A%20modern%20medical%20laboratory%20with%20advanced%20diagnostic%20equipment%2C%20scientists%20in%20lab%20coats%20working%20with%20microscopes%20and%20test%20tubes%2C%20clean%20and%20organized%20environment%20with%20computer%20monitors%20displaying%20test%20results%2C%20high%20quality%20professional%20photography&width=600&height=400&seq=5&orientation=landscape"
                    alt="Medical Laboratory"
                    className="w-full h-64 object-cover object-top"
                  />
                  <div className="p-6">
                    <Title level={4} className="mb-2">
                      State-of-the-Art Laboratory
                    </Title>
                    <Paragraph className="text-gray-600">
                      Our laboratory is equipped with cutting-edge diagnostic
                      equipment for accurate and timely test results.
                    </Paragraph>
                  </div>
                </Card>
              </Col>
            </Row>
          </div>

          {/* Service Quality Chart */}
          <div className="mb-16">
            <div className="text-center mb-10">
              <Title level={2} className="text-3xl font-bold">
                Our Service Quality
              </Title>
              <Paragraph className="text-gray-600 max-w-2xl mx-auto">
                We are committed to maintaining the highest standards of
                healthcare service quality.
              </Paragraph>
            </div>

            <Row gutter={[24, 24]} align="middle">
              <Col xs={24} md={12}>
                <div
                  id="serviceChart"
                  style={{ width: "100%", height: "400px" }}
                ></div>
              </Col>
              <Col xs={24} md={12}>
                <Card className="shadow-md">
                  <Title level={3} className="text-xl font-bold mb-4">
                    Why Choose Us?
                  </Title>
                  <ul className="space-y-4">
                    <li className="flex items-start">
                      <div className="mr-3 mt-1 text-blue-500">
                        <i className="fas fa-check-circle"></i>
                      </div>
                      <div>
                        <Text strong>Experienced Medical Team</Text>
                        <Paragraph className="text-gray-600 mt-1">
                          Our doctors and medical staff have years of experience
                          and are experts in their respective fields.
                        </Paragraph>
                      </div>
                    </li>
                    <li className="flex items-start">
                      <div className="mr-3 mt-1 text-blue-500">
                        <i className="fas fa-check-circle"></i>
                      </div>
                      <div>
                        <Text strong>Patient-Centered Approach</Text>
                        <Paragraph className="text-gray-600 mt-1">
                          We prioritize patient comfort and satisfaction in all
                          aspects of our healthcare services.
                        </Paragraph>
                      </div>
                    </li>
                    <li className="flex items-start">
                      <div className="mr-3 mt-1 text-blue-500">
                        <i className="fas fa-check-circle"></i>
                      </div>
                      <div>
                        <Text strong>Advanced Technology</Text>
                        <Paragraph className="text-gray-600 mt-1">
                          We utilize the latest medical technology and equipment
                          for accurate diagnosis and effective treatment.
                        </Paragraph>
                      </div>
                    </li>
                  </ul>
                </Card>
              </Col>
            </Row>
          </div>

          {/* Testimonials */}
          <div className="mb-16">
            <div className="text-center mb-10">
              <Title level={2} className="text-3xl font-bold">
                What Our Patients Say
              </Title>
              <Paragraph className="text-gray-600 max-w-2xl mx-auto">
                Read testimonials from our satisfied patients about their
                experience with our healthcare services.
              </Paragraph>
            </div>

            <Row gutter={[24, 24]}>
              <Col xs={24} md={8}>
                <Card className="h-full shadow-md">
                  <div className="flex flex-col h-full">
                    <div className="text-yellow-400 mb-4">
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                    </div>
                    <Paragraph className="text-gray-600 flex-grow">
                      "The medical staff was extremely professional and caring.
                      They took the time to explain my condition and treatment
                      options. I felt well taken care of throughout my stay."
                    </Paragraph>
                    <div className="mt-4 pt-4 border-t border-gray-200">
                      <Text strong>Sarah Johnson</Text>
                      <Text className="block text-gray-500">
                        Cardiology Patient
                      </Text>
                    </div>
                  </div>
                </Card>
              </Col>
              <Col xs={24} md={8}>
                <Card className="h-full shadow-md">
                  <div className="flex flex-col h-full">
                    <div className="text-yellow-400 mb-4">
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                    </div>
                    <Paragraph className="text-gray-600 flex-grow">
                      "I was impressed by the efficiency and cleanliness of the
                      facility. The doctors were knowledgeable and took their
                      time with me. The follow-up care was excellent."
                    </Paragraph>
                    <div className="mt-4 pt-4 border-t border-gray-200">
                      <Text strong>Michael Chen</Text>
                      <Text className="block text-gray-500">
                        Orthopedic Patient
                      </Text>
                    </div>
                  </div>
                </Card>
              </Col>
              <Col xs={24} md={8}>
                <Card className="h-full shadow-md">
                  <div className="flex flex-col h-full">
                    <div className="text-yellow-400 mb-4">
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                      <i className="fas fa-star"></i>
                    </div>
                    <Paragraph className="text-gray-600 flex-grow">
                      "The health check-up process was smooth and thorough. The
                      staff was friendly and made me feel comfortable. I
                      received my results quickly with clear explanations."
                    </Paragraph>
                    <div className="mt-4 pt-4 border-t border-gray-200">
                      <Text strong>Emily Rodriguez</Text>
                      <Text className="block text-gray-500">
                        Regular Check-up Patient
                      </Text>
                    </div>
                  </div>
                </Card>
              </Col>
            </Row>
          </div>

          {/* Call to Action */}
          <div className="bg-blue-600 rounded-lg p-8 text-center text-white">
            <Title level={2} className="!text-white text-3xl font-bold mb-4">
              Ready to Experience Quality Healthcare?
            </Title>
            <Paragraph className="text-white text-lg mb-6 max-w-2xl mx-auto">
              Book an appointment today and take the first step towards better
              health. Our team of medical professionals is ready to provide you
              with the best care.
            </Paragraph>
            <Space size="large">
              <Button
                size="large"
                className="bg-white text-blue-600 hover:bg-gray-100 !rounded-button whitespace-nowrap cursor-pointer"
              >
                Book Appointment
              </Button>
              <Button
                ghost
                size="large"
                className="text-white border-white hover:text-blue-200 hover:border-blue-200 !rounded-button whitespace-nowrap cursor-pointer"
              >
                Contact Us
              </Button>
            </Space>
          </div>
        </div>
      </Content>
    </>
  );
};

export default MainContentComponent;
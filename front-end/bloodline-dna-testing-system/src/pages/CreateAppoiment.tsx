// The exported code uses Tailwind CSS. Install Tailwind CSS in your dev environment to ensure all styles work.

import React, { useState } from "react";
import {
  Button,
  Card,
  DatePicker,
  Form,
  Input,
  InputNumber,
  Select,
  Typography,
  Divider,
  Steps,
  message,
} from "antd";
import {
  UserOutlined,
  PhoneOutlined,
  MailOutlined,
  ClockCircleOutlined,
  MedicineBoxOutlined,
} from "@ant-design/icons";
import HeaderComponent from "../components/Header";
import FooterComponent from "../components/Footer";

const { Title, Text, Paragraph } = Typography;
const { Option } = Select;
const { TextArea } = Input;

const CreateAppointment: React.FC = () => {
  const [form] = Form.useForm();
  const [currentStep, setCurrentStep] = useState(0);
  const [loading, setLoading] = useState(false);

  const onFinish = (values: any) => {
    setLoading(true);
    console.log("Form values:", values);

    // Simulate API call
    setTimeout(() => {
      setLoading(false);
      message.success("Your service request has been submitted successfully!");
      form.resetFields();
      setCurrentStep(0);
    }, 1500);
  };
  

  const nextStep = async () => {
    try {
      if (currentStep === 0) {
        await form.validateFields(["fullName", "email", "phone"]);
      } else if (currentStep === 1) {
        await form.validateFields([
          "serviceType",
          "appointmentDate",
          "preferredTime",
        ]);
      }
      setCurrentStep(currentStep + 1);
    } catch (error) {
      console.log("Validation failed:", error);
    }
  };

  const prevStep = () => {
    setCurrentStep(currentStep - 1);
  };

  const serviceTypes = [
    { value: "blood-test", label: "Blood Test" },
    { value: "urine-analysis", label: "Urine Analysis" },
    { value: "covid-test", label: "COVID-19 Test" },
    { value: "allergy-test", label: "Allergy Test" },
    { value: "genetic-test", label: "Genetic Test" },
    { value: "hormone-test", label: "Hormone Test" },
  ];

  const timeSlots = [
    "08:00 AM - 09:00 AM",
    "09:00 AM - 10:00 AM",
    "10:00 AM - 11:00 AM",
    "11:00 AM - 12:00 PM",
    "01:00 PM - 02:00 PM",
    "02:00 PM - 03:00 PM",
    "03:00 PM - 04:00 PM",
    "04:00 PM - 05:00 PM",
  ];

  const steps = [
    {
      title: "Personal Information",
      content: (
        <div className="space-y-6">
          <Form.Item
            name="fullName"
            label="Full Name"
            rules={[{ required: true, message: "Please enter your full name" }]}
          >
            <Input
              prefix={<UserOutlined className="text-gray-400" />}
              placeholder="Enter your full name"
            />
          </Form.Item>

          <Form.Item
            name="email"
            label="Email Address"
            rules={[
              { required: true, message: "Please enter your email" },
              { type: "email", message: "Please enter a valid email" },
            ]}
          >
            <Input
              prefix={<MailOutlined className="text-gray-400" />}
              placeholder="Enter your email address"
            />
          </Form.Item>

          <Form.Item
            name="phone"
            label="Phone Number"
            rules={[
              { required: true, message: "Please enter your phone number" },
            ]}
          >
            <Input
              prefix={<PhoneOutlined className="text-gray-400" />}
              placeholder="Enter your phone number"
            />
          </Form.Item>

          <Form.Item name="address" label="Address (Optional)">
            <Input placeholder="Enter your address" />
          </Form.Item>
        </div>
      ),
    },
    {
      title: "Service Details",
      content: (
        <div className="space-y-6">
          <Form.Item
            name="serviceType"
            label="Service Type"
            rules={[
              { required: true, message: "Please select a service type" },
            ]}
          >
            <Select placeholder="Select service type">
              {serviceTypes.map((service) => (
                <Option key={service.value} value={service.value}>
                  {service.label}
                </Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            name="appointmentDate"
            label="Appointment Date"
            rules={[{ required: true, message: "Please select a date" }]}
          >
            <DatePicker
              className="w-full"
              format="YYYY-MM-DD"
              placeholder="Select date"
              disabledDate={(current) => {
                // Can't select days before today
                return (
                  current && current < new Date(new Date().setHours(0, 0, 0, 0))
                );
              }}
            />
          </Form.Item>

          <Form.Item
            name="preferredTime"
            label="Preferred Time"
            rules={[{ required: true, message: "Please select a time slot" }]}
          >
            <Select placeholder="Select preferred time">
              {timeSlots.map((slot) => (
                <Option key={slot} value={slot}>
                  {slot}
                </Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            name="quantity"
            label="Number of Tests"
            initialValue={1}
            rules={[{ required: true, message: "Please enter quantity" }]}
          >
            <InputNumber min={1} max={10} className="w-full" />
          </Form.Item>
        </div>
      ),
    },
    {
      title: "Additional Information",
      content: (
        <div className="space-y-6">
          <Form.Item name="medicalHistory" label="Medical History (Optional)">
            <TextArea
              rows={4}
              placeholder="Please provide any relevant medical history"
            />
          </Form.Item>

          <Form.Item name="allergies" label="Allergies (Optional)">
            <Input placeholder="List any allergies you have" />
          </Form.Item>

          <Form.Item
            name="currentMedications"
            label="Current Medications (Optional)"
          >
            <TextArea
              rows={3}
              placeholder="List any medications you are currently taking"
            />
          </Form.Item>

          <Form.Item name="additionalNotes" label="Additional Notes">
            <TextArea
              rows={4}
              placeholder="Any additional information you'd like us to know"
            />
          </Form.Item>
        </div>
      ),
    },
    {
      title: "Review & Submit",
      content: (
        <div className="space-y-6">
          <Card className="bg-blue-50 border-blue-200">
            <Title level={5} className="text-blue-800 mb-4">
              Order Summary
            </Title>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <Text className="text-gray-500 block">Full Name</Text>
                <Text strong className="text-lg">
                  {form.getFieldValue("fullName") || "Not provided"}
                </Text>
              </div>

              <div>
                <Text className="text-gray-500 block">Contact</Text>
                <Text>{form.getFieldValue("phone") || "Not provided"}</Text>
              </div>

              <div>
                <Text className="text-gray-500 block">Email</Text>
                <Text>{form.getFieldValue("email") || "Not provided"}</Text>
              </div>

              <div>
                <Text className="text-gray-500 block">Service Type</Text>
                <Text strong>
                  {serviceTypes.find(
                    (s) => s.value === form.getFieldValue("serviceType")
                  )?.label || "Not selected"}
                </Text>
              </div>

              <div>
                <Text className="text-gray-500 block">Appointment Date</Text>
                <Text>
                  {form.getFieldValue("appointmentDate")
                    ? form.getFieldValue("appointmentDate").format("YYYY-MM-DD")
                    : "Not selected"}
                </Text>
              </div>

              <div>
                <Text className="text-gray-500 block">Preferred Time</Text>
                <Text>
                  {form.getFieldValue("preferredTime") || "Not selected"}
                </Text>
              </div>

              <div>
                <Text className="text-gray-500 block">Number of Tests</Text>
                <Text>{form.getFieldValue("quantity") || "1"}</Text>
              </div>
            </div>

            <Divider className="my-4" />

            <div>
              <Text className="text-gray-500 block">Additional Notes</Text>
              <Text>
                {form.getFieldValue("additionalNotes") || "None provided"}
              </Text>
            </div>
          </Card>

          <div className="bg-yellow-50 p-4 rounded-lg border border-yellow-200">
            <Text className="text-yellow-800">
              <i className="fas fa-info-circle mr-2"></i>
              By submitting this form, you agree to our terms and conditions and
              privacy policy.
            </Text>
          </div>
        </div>
      ),
    },
  ];

  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-50 to-gray-50">
      <HeaderComponent />

      {/* Main Content */}
      <div className="container mx-auto px-4 py-8">
        <div className="max-w-4xl mx-auto mt-20">
            <div className="mb-8 text-center">
            <Title level={2} className="text-blue-800">
              Schedule Your Service
            </Title>
            <Paragraph className="text-gray-600 text-lg">
              Complete the form below to request your healthcare service. Our
              team will contact you to confirm your appointment.
            </Paragraph>
          </div>

          <Card className="shadow-lg rounded-xl border-none !mb-5">
            <Steps
              current={currentStep}
              items={steps.map((item) => ({ title: item.title }))}
              className="mb-8"
            />

            <Form
              form={form}
              layout="vertical"
              onFinish={onFinish}
              className="mt-6"
            >
              {steps[currentStep].content}

              <div className="flex justify-between mt-8">
                {currentStep > 0 && (
                  <Button
                    onClick={prevStep}
                    className="!rounded-button cursor-pointer whitespace-nowrap"
                  >
                    Previous
                  </Button>
                )}

                <div className="ml-auto">
                  {currentStep < steps.length - 1 && (
                    <Button
                      type="primary"
                      onClick={nextStep}
                      className="!rounded-button cursor-pointer whitespace-nowrap bg-blue-600 hover:bg-blue-700"
                    >
                      Next
                    </Button>
                  )}

                  {currentStep === steps.length - 1 && (
                    <Button
                      type="primary"
                      htmlType="submit"
                      loading={loading}
                      className="!rounded-button cursor-pointer whitespace-nowrap bg-blue-600 hover:bg-blue-700"
                    >
                      Submit Order
                    </Button>
                  )}
                </div>
              </div>
            </Form>
          </Card>

          {/* Service Features */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
            <Card className="text-center shadow-md hover:shadow-lg transition-shadow duration-300 rounded-xl border-none">
              <div className="text-blue-600 text-4xl mb-4">
                <MedicineBoxOutlined />
              </div>
              <Title level={5}>Professional Testing</Title>
              <Text className="text-gray-600">
                Our certified technicians ensure accurate and reliable test
                results
              </Text>
            </Card>

            <Card className="text-center shadow-md hover:shadow-lg transition-shadow duration-300 rounded-xl border-none">
              <div className="text-blue-600 text-4xl mb-4">
                <ClockCircleOutlined />
              </div>
              <Title level={5}>Fast Results</Title>
              <Text className="text-gray-600">
                Get your test results quickly through our secure online portal
              </Text>
            </Card>

            <Card className="text-center shadow-md hover:shadow-lg transition-shadow duration-300 rounded-xl border-none">
              <div className="text-blue-600 text-4xl mb-4">
                <i className="fas fa-shield-alt"></i>
              </div>
              <Title level={5}>Privacy Guaranteed</Title>
              <Text className="text-gray-600">
                Your personal information and test results are kept strictly
                confidential
              </Text>
            </Card>
          </div>
        </div>
      </div>

      {/* Testimonial Section */}
      <div className="bg-blue-600 py-12">
        <div className="container mx-auto px-4">
          <div className="max-w-4xl mx-auto text-center text-white">
            <i className="fas fa-quote-left text-4xl opacity-50 mb-4"></i>
            <Paragraph className="text-xl font-light italic mb-6">
              "AND TEST provided exceptional service for my annual health
              checkup. The process was smooth, the staff was professional, and I
              received my results promptly. I highly recommend their services to
              anyone looking for quality healthcare testing."
            </Paragraph>
            <div className="font-semibold">Sarah Johnson</div>
            <div className="text-blue-200">Satisfied Customer</div>
          </div>
        </div>
      </div>

      <FooterComponent />
    </div>
  );
};

export default CreateAppointment;

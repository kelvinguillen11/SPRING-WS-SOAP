package sv.edu.udb.www.api.endpoint;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import sv.edu.udb.www.api.converter.ProductConverter;
import sv.edu.udb.www.api.entity.Product;
import sv.edu.udb.www.api.generated.*;
import sv.edu.udb.www.api.model.ProductModel;
import sv.edu.udb.www.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import sv.edu.udb.www.api.service.IProductService;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://www.udb.edu.sv/api/generated";

    @Autowired
    private IProductService ProductServices;




    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProduct(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        ProductInfo product = new ProductInfo();
        BeanUtils.copyProperties(ProductServices.getProductById(request.getId()),product);
        response.setProduct(product);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts() {

        GetAllProductsResponse response= new GetAllProductsResponse();

        List<ProductInfo> productInfoList = new ArrayList<>();
        List<Product> products = ProductServices.getAllProducts();
        for (int i = 0; i < products.size(); i++) {
            ProductInfo obj = new ProductInfo();
            BeanUtils.copyProperties(products.get(i), obj);
            productInfoList.add(obj);
        }
        response.getProducts().addAll(productInfoList);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addProductRequest")
    @ResponsePayload
    public AddProductResponse addProduct(@RequestPayload AddProductRequest request) {

        AddProductResponse response = new AddProductResponse();
        ProcessStatus processStatus = new ProcessStatus();
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());
        boolean flag = ProductServices.addProduct(product);
        if (flag == false) {
            processStatus.setStatusCode("ERROR");
            processStatus.setMessage("Ya existe un registro con ese nombre");
            response.setProcessStatus(processStatus);
        } else {
            ProductInfo productInfo = new ProductInfo();
            BeanUtils.copyProperties(product, productInfo);
            response.setProduct(productInfo);
            processStatus.setStatusCode("Exito");
            processStatus.setMessage("Registro agregado correctamente");
            response.setProcessStatus(processStatus);
        }
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postProductRequest")
    @ResponsePayload
    public PostProductResponse postProducts(@RequestPayload PostProductRequest request) {
        PostProductResponse response = new PostProductResponse();
        ProductModel productModel = productConverter.convertProductToProductModel(request.getProduct());
        Product product = productConverter.convertProductModelToProduct(productRepository.save(productModel));
        response.setProduct(product);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProducts(@RequestPayload GetProductsRequest request) {
        GetProductsResponse response = new GetProductsResponse();
        List<ProductModel> productModels = productRepository.findAll();
        List<Product> products = productConverter.convertProductModelsToProducts(productModels);
        response.getProducts().addAll(products);
        return response;
    }
}
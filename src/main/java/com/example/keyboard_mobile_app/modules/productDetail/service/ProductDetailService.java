package com.example.keyboard_mobile_app.modules.productDetail.service;
import com.example.keyboard_mobile_app.entity.ProductDetail;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.productDetail.repository.ProductDetailRepository;
import com.example.keyboard_mobile_app.shared.UploadImageService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductDetailService {
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private UploadImageService uploadImageService;

    public ResponseBase createProductDetail(MultipartFile[] displayImage, ProductDetail productDetail) throws IOException {
        List<String> lstImage = new ArrayList<>();
        String descriptionImageLists;
        if (displayImage != null) {
            List<String> imageUrls = uploadImageService.uploadFiles(displayImage);
            lstImage.addAll(imageUrls);
            descriptionImageLists = new Gson().toJson(lstImage);
            productDetail.setImageUrl(descriptionImageLists);
        }
        return new ResponseBase(
                "Create product detail successfully!",
                productDetailRepository.createProduct(productDetail)
        );
    }
    public ResponseBase getById(String id) throws InterruptedException, ExecutionException {
        ProductDetail result = productDetailRepository.getById(id);
        if(result != null) {
            return new ResponseBase(
                    "Product Detail found!",
                    result
            );
        }
        else return new ResponseBase(
                "Product Detail Not found!",
                null
        );
    }
    public ResponseBase getListProductDetail() throws ExecutionException, InterruptedException {
        List<ProductDetail> result = productDetailRepository.getListProductDetail();
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Product Detail",
                    result
            );
        } else {
            return new ResponseBase(
                    "No Product found!",
                    null
            );
        }
    }
    public ResponseBase deleteDetail(String id) throws InterruptedException, ExecutionException {
        String result = productDetailRepository.deleteDetail(id);
        return new ResponseBase(
                result.toString(),
                null
        );
    }
    public ResponseBase getListByProductId(String productId) throws ExecutionException, InterruptedException {
        List<ProductDetail> result = productDetailRepository.getByProductId(productId);
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Product Detail By ProductId",
                    result
            );
        } else {
            return new ResponseBase(
                    "No Product found!",
                    null
            );
        }
    }
    public boolean checkSl(String productDetailId, int quantity) throws ExecutionException, InterruptedException {
        return productDetailRepository.checkSl(productDetailId, quantity);
    }
}

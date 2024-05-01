<?php

use App\Http\Controllers\MataKuliahController;
use App\Http\Controllers\SesiKuliahController;
use App\Http\Controllers\SoalController;
use App\Http\Controllers\TestController;
use App\Http\Controllers\UserController;
use Illuminate\Support\Facades\Route;



Route::get('/', function () {
    return view('index');
});
Route::group(['prefix' => 'users', 'as' => 'users.'], function () {
    Route::get('/', [UserController::class, 'index'])->name('/');
});
Route::group(['prefix' => 'mata-kuliah', 'as' => 'mata-kuliah.'], function () {
    Route::get('/', [MataKuliahController::class, 'index'])->name('/');
    Route::post('/create', [MataKuliahController::class, 'create'])->name('create');
    Route::post('/update', [MataKuliahController::class, 'update'])->name('update');
    Route::post('/delete', [MataKuliahController::class, 'delete'])->name('delete');
});
Route::group(['prefix' => 'sesi-kuliah', 'as' => 'sesi-kuliah.'], function () {
    Route::get('/{mata_kuliah_code}', [SesiKuliahController::class, 'index'])->name('/');
    Route::post('/create', [SesiKuliahController::class, 'create'])->name('create');
    Route::post('/generateqr', [SesiKuliahController::class, 'generateQr'])->name('generateqr');
});
Route::group(['prefix' => 'test', 'as' => 'test.'], function () {
    Route::get('/{mata_kuliah_code}', [TestController::class, 'index'])->name('/');
    Route::post('/create', [TestController::class, 'create'])->name('create');
    Route::get('/generateqr/{test_code}', [TestController::class, 'generateQr'])->name('generateqr');
});
Route::group(['prefix' => 'soal', 'as' => 'soal.'], function () {
    Route::get('/{test_code}', [SoalController::class, 'index'])->name('/');
    Route::post('/create', [SoalController::class, 'create'])->name('create');
});